/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author user
 */
@Component
public class HttpErrorResponseHandler {

    @Autowired
    private Environment env;

    public ResponseEntity<?> handleBadRequest(BindingResult bindingResult) {
        Map<String, Map<String, String>> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        response.put(env.getProperty("mes.message"), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public ResponseEntity<?> handleBadRequest(String errMessage) {
        Map<String, String> response = new HashMap<>();
        response.put(env.getProperty("mes.message"), errMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public ResponseEntity<?> handleUnauthorizedRequest(String errMessage) {
        Map<String, String> response = new HashMap<>();
        response.put(env.getProperty("mes.message"), errMessage);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public ResponseEntity<?> handleInternalServerError(String errMessage) {
        Map<String, String> response = new HashMap<>();
        response.put(env.getProperty("mes.message"), errMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public ResponseEntity<?> handleForbiddenRequest(Object object) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(object);
    }

    public ResponseEntity<?> handleForbiddenRequest(String errMessage) {
        Map<String, String> response = new HashMap<>();
        response.put(env.getProperty("mes.message"), errMessage);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
