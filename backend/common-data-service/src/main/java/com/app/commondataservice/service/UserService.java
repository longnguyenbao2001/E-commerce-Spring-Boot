/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.entity.Users;
import java.util.Optional;

/**
 *
 * @author user
 */
public interface UserService {

    public Optional<Users> getUserByUsername(String username);
}
