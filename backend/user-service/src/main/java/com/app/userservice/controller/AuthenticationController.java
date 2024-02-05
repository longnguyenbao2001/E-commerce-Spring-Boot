/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.controller;

import com.app.userservice.dto.LoginUserRequestDTO;
import com.app.userservice.dto.LoginUserResponseDTO;
import com.app.userservice.dto.NewUserRequestDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.UserAlreadyExistsException;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody NewUserRequestDTO newUserRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            userService.signUp(newUserRequestDTO);
            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.signup.success"));
        } catch (UserAlreadyExistsException ex) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.signup.existed"));
        } catch (EmailFailureException ex) {
            return httpErrorResponseHandler.handleInternalServerError(ex.getMessage());
        } catch (Exception ex) {
            return httpErrorResponseHandler.handleBadRequest(ex.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            String jwt = userService.signIn(loginUserRequestDTO);
            if (jwt == null) {
                return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.signin.incorrect"));
            }

            LoginUserResponseDTO loginUserResponseDTO = new LoginUserResponseDTO();
            loginUserResponseDTO.setJwt(jwt);
            loginUserResponseDTO.setSuccess(true);

            return httpResponseHandler.handleAcceptedRequest(loginUserResponseDTO);
        } catch (UserNotVerifiedException ex) {
            LoginUserResponseDTO loginUserResponseDTO = new LoginUserResponseDTO();
            loginUserResponseDTO.setSuccess(false);
            loginUserResponseDTO.setMessage(env.getProperty("mes.signin.notVerified"));

            return httpErrorResponseHandler.handleForbiddenRequest(loginUserResponseDTO);
        } catch (EmailFailureException ex) {
            return httpErrorResponseHandler.handleInternalServerError(ex.getMessage());
        } catch (Exception ex) {
            return httpErrorResponseHandler.handleBadRequest(ex.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } else {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.badRequest"));
        }
    }

    @GetMapping("/me")
    public Users getLoggedInUserProfile(@AuthenticationPrincipal Users user) {
        return user;
    }
}
