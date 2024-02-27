/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.dto;

/**
 *
 * @author user
 */
public class SignInUserResponseDTO {

    private String jwtAccessToken;

    private UserDTO userData;

    public String getJwtAccessToken() {
        return jwtAccessToken;
    }

    public void setJwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    public UserDTO getUserData() {
        return userData;
    }

    public void setUserData(UserDTO userData) {
        this.userData = userData;
    }
}
