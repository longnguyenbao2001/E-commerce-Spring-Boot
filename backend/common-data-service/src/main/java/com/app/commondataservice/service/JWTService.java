/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.entity.Roles;
import com.app.commondataservice.entity.Users;

/**
 *
 * @author user
 */
public interface JWTService {

    public String getUsername(String token);

    public String getRoleName(String token);
}
