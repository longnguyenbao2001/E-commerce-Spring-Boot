/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.service.CallApiService;
import com.app.commondataservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author user
 */
@Service
public class CallApiServiceImpl implements CallApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Environment env;

    @Override
    public AuthUserDTO authenticate(String accessTokenHeader) throws UserNotFoundException {
        try {
            Mono<AuthUserDTO> monoAuthUser = webClientBuilder.baseUrl(env.getProperty("api.userservice.url"))
                    .defaultHeader(HttpHeaders.AUTHORIZATION, accessTokenHeader)
                    .build()
                    .get()
                    .uri(env.getProperty("api.userservice.endpoint.authentication"))
                    .retrieve()
                    .bodyToMono(AuthUserDTO.class);

            AuthUserDTO authUser = monoAuthUser.block();

            return authUser;
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }
}
