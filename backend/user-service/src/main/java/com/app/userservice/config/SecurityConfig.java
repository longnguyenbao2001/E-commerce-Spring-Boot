/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.config;

import com.app.userservice.component.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author user
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeRequests(authorizeRequests
                        -> authorizeRequests
                        .requestMatchers("/auth/signup",
                                "/auth/signin",
                                "/auth/verify",
                                "/auth/forgot",
                                "/auth/reset",
                                "/auth/authenticate").permitAll()
                        .requestMatchers("/auth/me").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
