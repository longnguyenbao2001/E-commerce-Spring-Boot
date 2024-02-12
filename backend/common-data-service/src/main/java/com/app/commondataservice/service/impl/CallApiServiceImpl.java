/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.service.CallApiService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
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
    private RestTemplate restTemplate;

    @Override
    public AuthUserDTO authenticate(String accessTokenHeader) {

        Mono<AuthUserDTO> result = webClientBuilder.baseUrl("http://localhost:8080/auth")
                .defaultHeader(HttpHeaders.AUTHORIZATION, accessTokenHeader)
                .build()
                .get()
                .uri("/authenticate")
                .retrieve()
                .bodyToMono(AuthUserDTO.class);

        return result.block();
    }
}