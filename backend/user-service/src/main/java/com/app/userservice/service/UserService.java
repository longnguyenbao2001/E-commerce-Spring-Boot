/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.ForgotPasswordRequestDTO;
import com.app.userservice.dto.ResetPasswordRequestDTO;
import com.app.userservice.dto.SignInUserRequestDTO;
import com.app.userservice.dto.SignUpUserRequestDTO;
import com.app.userservice.dto.SignInUserResponseDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.EmailNotAssosiatedWithUserException;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.exception.UserNotExistedException;
import com.app.userservice.exception.UserNotVerifiedException;
import java.util.Optional;

/**
 *
 * @author user
 */
public interface UserService {

    public Optional<Users> getUserByUsername(String username);

    public Optional<Users> getUserByUserId(Long userId)
            throws UserNotExistedException;

    public UserDTO signUp(SignUpUserRequestDTO signUpUserRequestDTO)
            throws UserAlreadyExistsException, EmailFailureException;

    public SignInUserResponseDTO signIn(SignInUserRequestDTO signInUserRequestDTO)
            throws UserNotVerifiedException, EmailFailureException, UserNotExistedException;

    public boolean verifyUser(String token)
            throws UserNotExistedException;

    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO)
            throws UserNotExistedException, EmailNotAssosiatedWithUserException, EmailFailureException;

    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO)
            throws UserNotExistedException, EmailNotAssosiatedWithUserException;
}
