/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.service;

import com.app.orderservice.dto.AuthUserDTO;
import com.app.orderservice.exception.UserNotFoundException;

/**
 *
 * @author user
 */
public interface CallApiService {

    public AuthUserDTO authenticate(String accessTokenHeader)
            throws UserNotFoundException;
}
