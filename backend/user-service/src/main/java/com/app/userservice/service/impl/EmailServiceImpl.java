/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.dto.ForgotPasswordRequestDTO;
import com.app.userservice.entity.VerificationTokens;
import com.app.userservice.service.EmailService;
import com.app.userservice.exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${email.from}")
    private String fromAddress;
    @Value("${app.frontend.mail.verification.url}")
    private String url;
    @Value("${app.frontend.mail.verification.endpoint}")
    private String emailVerificationEndpoint;
    @Value("${app.frontend.mail.passwordReset.endpoint}")
    private String passwordResetEndpoint;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    private SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);

        return simpleMailMessage;
    }

    @Override
    public void sendVerificationEmail(VerificationTokens verificationTokens) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationTokens.getUsers().getEmail());
        message.setSubject(env.getProperty("mes.mail.verification.subject"));
        message.setText(String.format("%s%s%s%s",
                env.getProperty("mes.mail.verification.content"),
                url,
                emailVerificationEndpoint,
                verificationTokens.getToken()));

        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

    @Override
    public void sendPasswordResetEmail(ForgotPasswordRequestDTO forgotPasswordRequestDTO,
            String passwordResetToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(forgotPasswordRequestDTO.getEmail());
        message.setSubject(env.getProperty("mes.mail.resetPassword.subject"));
        message.setText(String.format("%s%s%s%s",
                env.getProperty("mes.mail.resetPassword.content"),
                url,
                passwordResetEndpoint,
                passwordResetToken));
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

}
