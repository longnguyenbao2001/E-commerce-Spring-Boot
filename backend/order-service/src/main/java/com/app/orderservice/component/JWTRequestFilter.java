/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.component;

import com.app.orderservice.dto.AuthUserDTO;
import com.app.orderservice.service.CallApiService;
import com.app.orderservice.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author user
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CallApiService callApiService;

    @Autowired
    private Environment env;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        try {
            AuthUserDTO authUserDTO = callApiService.authenticate(tokenHeader);

            List<GrantedAuthority> authorities;
            if (authUserDTO != null) {
                authorities = Collections.singletonList(new SimpleGrantedAuthority(authUserDTO.getRoleName()));
            } else {
                authorities = Collections.singletonList(new SimpleGrantedAuthority(env.getProperty("role.user")));
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUserDTO, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UserNotFoundException e) {
        } catch (Exception e) {
        }

        filterChain.doFilter(request, response);
    }
}
