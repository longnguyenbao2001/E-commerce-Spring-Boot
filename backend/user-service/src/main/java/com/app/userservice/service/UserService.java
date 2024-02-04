/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.LoginUserRequestDTO;
import com.app.userservice.dto.NewUserRequestDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.exception.UserAlreadyExistsException;

/**
 *
 * @author user
 */
public interface UserService {
    public UserDTO signUp(NewUserRequestDTO newUserRequestDTO) throws UserAlreadyExistsException;
    public String signIn(LoginUserRequestDTO loginUserRequestDTO);
}
