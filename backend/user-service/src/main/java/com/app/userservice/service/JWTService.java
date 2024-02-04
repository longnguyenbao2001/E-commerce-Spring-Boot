/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.entity.Users;

/**
 *
 * @author user
 */
public interface JWTService {
    public String generateJWT(Users user);
    public String getUsername(String token);
}
