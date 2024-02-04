/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.entity.Roles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.app.userservice.entity.Users;
import com.app.userservice.service.JWTService;
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
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;
//    private String USERNAME_KEY = env.getProperty("jwt.USERNAME_KEY");
//    private String ROLE_KEY = env.getProperty("jwt.ROLE_KEY");
    private static final String USERNAME_KEY ="USERNAME";
    private static final String ROLE_KEY = "ROLE";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    @Override
    public String generateJWT(Users user, Roles role) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withClaim(ROLE_KEY, role.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

    @Override
    public String getRoleName(String token) {
        return JWT.decode(token).getClaim(ROLE_KEY).asString();
    }
    
    
}
