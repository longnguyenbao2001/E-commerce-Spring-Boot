/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.entity.Roles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.service.JWTService;
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
//    private String USERNAME_KEY = env.getProperty("jwt.USERNAME_KEY");
//    private String ROLE_KEY = env.getProperty("jwt.ROLE_KEY");
    private static final String USERNAME_KEY = "USERNAME";
    private static final String ROLE_KEY = "ROLE";

    @Override
    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

    @Override
    public String getRoleName(String token) {
        return JWT.decode(token).getClaim(ROLE_KEY).asString();
    }

}
