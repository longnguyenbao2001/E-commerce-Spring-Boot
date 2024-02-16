/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.exception.UserNotFoundException;

/**
 *
 * @author user
 */
public interface CallApiService {

    public AuthUserDTO authenticate(String accessTokenHeader)
            throws UserNotFoundException;
}
