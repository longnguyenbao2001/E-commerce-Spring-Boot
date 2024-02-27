/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.UploadFilesResponseDTO;
import com.app.commondataservice.service.CallApiService;
import com.app.commondataservice.exception.UserNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
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

    @Override
    public UploadFilesResponseDTO uploadImages(List<MultipartFile> files) throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            parts.add("files", file.getResource());
        }

        Mono<UploadFilesResponseDTO> monoDownloadUrls = webClientBuilder.baseUrl(env.getProperty("api.firebaseservice.url"))
                .build()
                .post()
                .uri(env.getProperty("api.firebaseservice.endpoint.uploadImages"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .bodyToMono(UploadFilesResponseDTO.class);

        UploadFilesResponseDTO downloadUrls = monoDownloadUrls.block();

        return downloadUrls;
    }
}
