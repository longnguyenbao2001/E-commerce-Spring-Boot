/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.ForgotPasswordRequestDTO;
import com.app.userservice.entity.VerificationTokens;
import com.app.userservice.exception.EmailFailureException;

/**
 *
 * @author user
 */
public interface EmailService {

    public void sendVerificationEmail(VerificationTokens verificationToken) throws EmailFailureException;

    public void sendPasswordResetEmail(ForgotPasswordRequestDTO forgotPasswordRequestDTO, String passwordResetToken) throws EmailFailureException;
}
