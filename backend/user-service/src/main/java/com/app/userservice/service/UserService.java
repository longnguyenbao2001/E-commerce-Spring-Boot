/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.LoginUserRequestDTO;
import com.app.userservice.dto.NewUserRequestDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.EmailFailureException;
import com.app.userservice.exception.UserAlreadyExistsException;
import com.app.userservice.exception.UserNotVerifiedException;
import java.util.Optional;

/**
 *
 * @author user
 */
public interface UserService {

    public Optional<Users> findByUsername(String username);

    public UserDTO signUp(NewUserRequestDTO newUserRequestDTO) throws UserAlreadyExistsException, EmailFailureException;

    public String signIn(LoginUserRequestDTO loginUserRequestDTO) throws UserNotVerifiedException, EmailFailureException;

    public boolean verifyUser(String token);
}
