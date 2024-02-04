/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.converter.UserConverter;
import com.app.userservice.service.EncryptionService;
import com.app.userservice.service.JWTService;
import com.app.userservice.dao.UserRepository;
import com.app.userservice.dto.LoginUserRequestDTO;
import com.app.userservice.dto.NewUserRequestDTO;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.service.UserService;
import com.app.userservice.exception.UserAlreadyExistsException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private EncryptionService encryptionService;
    
    @Autowired
    private JWTService jWTService;

    public Optional<Users> findByUsername(String username) {
        Optional<Users> result = userRepository.findByUsername(username);

        return result;
    }

    @Override
    public UserDTO signUp(NewUserRequestDTO newUserRequestDTO) throws UserAlreadyExistsException {
        Optional<Users> existingUser = findByUsername(newUserRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Users user = new Users();
        user.setEmail(newUserRequestDTO.getEmail());
        user.setUsername(newUserRequestDTO.getUsername());
        user.setFirstName(newUserRequestDTO.getFirstName());
        user.setLastName(newUserRequestDTO.getLastName());
        user.setPassword(encryptionService.encryptPassword(newUserRequestDTO.getPassword()));

        user = userRepository.save(user);

        return userConverter.convertUsertoUserDTO(user);
    }

    @Override
    public String signIn(LoginUserRequestDTO loginUserRequestDTO) {
        Optional<Users> existingUser = findByUsername(loginUserRequestDTO.getUsername());
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            if(encryptionService.verifyPassword(loginUserRequestDTO.getPassword(), user.getPassword())) {
                return jWTService.generateJWT(user);
            }
        }
        return null;
    }
}
