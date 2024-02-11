/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.controller;

import com.app.userservice.component.DTOConverter;
import com.app.userservice.dto.ForgotPasswordRequestDTO;
import com.app.userservice.dto.ResetPasswordRequestDTO;
import com.app.userservice.dto.SignInUserRequestDTO;
import com.app.userservice.dto.SignInUserResponseDTO;
import com.app.userservice.dto.SignUpUserRequestDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.EmailNotAssosiatedWithUserException;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.exception.UserNotExistedException;
import com.app.userservice.exception.UserNotVerifiedException;
import com.app.userservice.handler.HttpErrorResponseHandler;
import com.app.userservice.handler.HttpResponseHandler;
import com.app.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @Autowired
    private DTOConverter userConverter;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpUserRequestDTO signUpUserRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            userService.signUp(signUpUserRequestDTO);
            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.signup.success"));
        } catch (UserAlreadyExistsException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.signup.existed"));
        } catch (EmailFailureException e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInUserRequestDTO loginUserRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            SignInUserResponseDTO res = userService.signIn(loginUserRequestDTO);
            if (res == null) {
                return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.signin.incorrect"));
            }

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (UserNotVerifiedException e) {
            return httpErrorResponseHandler.handleForbiddenRequest(env.getProperty("mes.signin.notVerified"));
        } catch (EmailFailureException e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        try {
            if (userService.verifyUser(token)) {
                return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.verification.success"));
            } else {
                return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.badRequest"));
            }
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUserProfile(@AuthenticationPrincipal Users user) {

        try {
            return httpResponseHandler.handleAcceptedRequest(userConverter.convertUserToUserDTO(user));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            userService.forgotPassword(forgotPasswordRequestDTO);
            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.password.forgot.success"));
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (EmailNotAssosiatedWithUserException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.email.notAssociated"));
        } catch (EmailFailureException e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            userService.resetPassword(resetPasswordRequestDTO);
            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.password.reset.success"));
        } catch (UserNotExistedException ex) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (EmailNotAssosiatedWithUserException ex) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.email.notAssociated"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
