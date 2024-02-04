/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.converter;

import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    
    public UserDTO convertUsertoUserDTO(Users user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }
}
