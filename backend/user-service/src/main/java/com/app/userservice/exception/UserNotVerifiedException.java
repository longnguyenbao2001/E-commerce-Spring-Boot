/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.exception;

/**
 *
 * @author user
 */
public class UserNotVerifiedException extends Exception {

    private boolean emailSentRecently;

    public UserNotVerifiedException(boolean emailSentRecently) {
        this.emailSentRecently = emailSentRecently;
    }

    public boolean isEmailSentRecently() {
        return emailSentRecently;
    }

}
