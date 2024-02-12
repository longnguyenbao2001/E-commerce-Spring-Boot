/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.controller;

import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.dto.SignUpUserRequestDTO;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.handler.HttpErrorResponseHandler;
import com.app.userservice.handler.HttpResponseHandler;
import com.app.userservice.service.UserPermissionService;
import com.app.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/permission")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @PostMapping("/isAdmin")
    public ResponseEntity<?> isAdmin(@RequestHeader("Authorization") String accessToken) {
        try {
            AuthUserDTO authUserDTO = userService.authenticate(accessToken);

            boolean isAdmin = userPermissionService.isAdmin(authUserDTO);
            if (isAdmin) {
                return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.user.permission.isAdmin"));
            }

            return httpErrorResponseHandler.handleUnauthorizedRequest(env.getProperty("mes.user.permission.unauthorized"));
        } catch (UserAlreadyExistsException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.signup.existed"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
