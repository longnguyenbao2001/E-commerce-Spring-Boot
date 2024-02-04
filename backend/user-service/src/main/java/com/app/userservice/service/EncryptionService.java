/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

/**
 *
 * @author user
 */
public interface EncryptionService {
    public String encryptPassword(String password);
    public boolean verifyPassword(String password, String hash);
}
