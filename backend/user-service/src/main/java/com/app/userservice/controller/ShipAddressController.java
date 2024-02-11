/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.controller;

import com.app.userservice.dto.CreateShipAddressRequestDTO;
import com.app.userservice.dto.PutShipAddressRequestDTO;
import com.app.userservice.dto.ShipAddressDTO;
import com.app.userservice.entity.ShipAddresses;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.DataNotFoundException;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotExistedException;
import com.app.userservice.handler.HttpErrorResponseHandler;
import com.app.userservice.handler.HttpResponseHandler;
import com.app.userservice.service.ShipAddressService;
import com.app.userservice.service.UserPermissionService;
import com.app.userservice.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ShipAddressService shipAddressService;

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<?> createShipAddress(@AuthenticationPrincipal Users user,
            @PathVariable Long userId,
            @Valid @RequestBody CreateShipAddressRequestDTO createShipAddressRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            if (userPermissionService.isPermittedToPerformAction(user, userId)) {
                createShipAddressRequestDTO.setUserId(userId);
                shipAddressService.createShipAddress(createShipAddressRequestDTO);

                return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
            }

            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.request.bad"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserHasNoPermissionException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notPermitted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<?> getShipAddress(@AuthenticationPrincipal Users user,
            @PathVariable Long userId) {
        try {
            if (userPermissionService.isPermittedToPerformAction(user, userId)) {
                List<ShipAddressDTO> shipAddresses = shipAddressService.getShipAddress(userId);

                return httpResponseHandler.handleAcceptedRequest(shipAddresses);
            }

            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.request.bad"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserHasNoPermissionException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notPermitted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<?> putShipAddress(@AuthenticationPrincipal Users user,
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody PutShipAddressRequestDTO putShipAddressRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            //check current log in user and current ref userid
            if (userPermissionService.isPermittedToPerformAction(user, userId)) {
                putShipAddressRequestDTO.setId(addressId);
                putShipAddressRequestDTO.setCurrUser(user);
                putShipAddressRequestDTO.setRefUserId(userId);
                shipAddressService.putShipAddress(putShipAddressRequestDTO);

                return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
            }

            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.request.bad"));
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserHasNoPermissionException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notPermitted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<?> deleteShipAddress(@AuthenticationPrincipal Users user,
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        try {
            if (userPermissionService.isPermittedToPerformAction(user, userId)) {
                shipAddressService.deleteShipAddress(addressId, user, userId);

                return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
            }

            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.request.bad"));
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserHasNoPermissionException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notPermitted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
