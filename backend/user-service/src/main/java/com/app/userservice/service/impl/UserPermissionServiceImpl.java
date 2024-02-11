/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.entity.Users;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private Environment env;

    @Override
    public boolean isOwner(Users user, Long refUserId) {
        if (user.getId().equals(refUserId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin(Users user) {
        if (user.getRoles() != null && user.getRoles().getName().equals(env.getProperty("role.admin"))) {
            return true;
        }
        return false;
    }
}
