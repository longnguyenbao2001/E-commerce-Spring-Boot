/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.entity.Users;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotExistedException;

/**
 *
 * @author user
 */
public interface UserPermissionService {

    public boolean isOwner(Users user, Long refUserId);

    public boolean isAdmin(Users user);
}
