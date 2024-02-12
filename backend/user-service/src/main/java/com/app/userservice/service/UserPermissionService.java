/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.exception.UserNotExistedException;

/**
 *
 * @author user
 */
public interface UserPermissionService {

    public boolean isOwner(Long authUserId, Long refUserId);

    public boolean isAdmin(AuthUserDTO authUserDTO)
            throws UserNotExistedException;
}
