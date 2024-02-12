/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.controller;

import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.dto.CreateShipAddressRequestDTO;
import com.app.userservice.dto.PutShipAddressRequestDTO;
import com.app.userservice.dto.ShipAddressDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.DataNotFoundException;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotExistedException;
import com.app.userservice.handler.HttpErrorResponseHandler;
import com.app.userservice.handler.HttpResponseHandler;
import com.app.userservice.service.ShipAddressService;
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
@RequestMapping("/addresses")
public class ShipAddressController {

    @Autowired
    private ShipAddressService shipAddressService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @GetMapping("/get")
    public ResponseEntity<?> getShipAddress(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        try {
            List<ShipAddressDTO> shipAddresses = shipAddressService.getShipAddressByUser(authUserDTO);

            return httpResponseHandler.handleAcceptedRequest(shipAddresses);
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createShipAddress(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @Valid @RequestBody CreateShipAddressRequestDTO createShipAddressRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            shipAddressService.createShipAddress(createShipAddressRequestDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserHasNoPermissionException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notPermitted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> putShipAddress(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long addressId,
            @Valid @RequestBody PutShipAddressRequestDTO putShipAddressRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            //check current log in user is the owner of ref userid
            putShipAddressRequestDTO.setId(addressId);
            shipAddressService.putShipAddress(putShipAddressRequestDTO, authUserDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
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

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteShipAddress(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long addressId) {
        try {
            shipAddressService.deleteShipAddress(addressId, authUserDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
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
