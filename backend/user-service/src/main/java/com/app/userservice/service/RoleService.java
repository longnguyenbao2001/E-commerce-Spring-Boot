/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import java.util.Optional;
import com.app.userservice.entity.Roles;
import java.util.List;

/**
 *
 * @author user
 */
public interface RoleService {

    public List<Roles> getRoleByName(String name);
}
