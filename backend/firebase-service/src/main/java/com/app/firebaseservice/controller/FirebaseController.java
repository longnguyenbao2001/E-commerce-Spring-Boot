/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.firebaseservice.controller;

import com.app.firebaseservice.handler.HttpErrorResponseHandler;
import com.app.firebaseservice.handler.HttpResponseHandler;
import com.app.firebaseservice.service.FirebaseStorageService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    @Autowired
    private Environment env;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @PostMapping("/products/uploadImage")
    public ResponseEntity<?> uploadProductImage(@RequestParam(name = "file", required = false) MultipartFile file) {
        try {
            String res = firebaseStorageService.uploadProductImage(file);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (IOException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.request.bad"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
