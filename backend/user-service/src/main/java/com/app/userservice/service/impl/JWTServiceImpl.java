/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.app.userservice.entity.Users;
import com.app.userservice.service.JWTService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author user
 */
@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    private Environment env;

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.accessToken.expiryTimeInMilliSeconds}")
    private int accessTokenExpiryTime;
    @Value("${jwt.verificationToken.expiryTimeInMilliSeconds}")
    private int verificationTokenExpiryTime;
    @Value("${jwt.passwordResetToken.expiryTimeInMilliSeconds}")
    private int passwordResetTokenExpiryTime;
    private Algorithm algorithm;
//    private static final String USERNAME_KEY = "USERNAME";
    private static final String USERID_KEY = "USERID";
    private static final String VERIFICATION_EMAIL_KEY = "VERIFICATION_EMAIL";
    private static final String PASSWORD_RESET_EMAIL_KEY = "PASSWORD_RESET_EMAIL";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    @Override
    public String generateJWTAccessToken(Users user) {
        return JWT.create()
                .withClaim(USERID_KEY, user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    //ExpiresAt much later after resend time limit
    @Override
    public String generateJWTVerificationToken(Users user) {
        return JWT.create()
                .withClaim(VERIFICATION_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + verificationTokenExpiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public String generateJWTPasswordResetToken(Users user) {
        return JWT.create()
                .withClaim(USERID_KEY, user.getId())
                .withClaim(PASSWORD_RESET_EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + passwordResetTokenExpiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public Long getUserId(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
            return jwt.getClaim(USERID_KEY).asLong();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public String getPasswordResetEmail(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(PASSWORD_RESET_EMAIL_KEY).asString();
    }
}
