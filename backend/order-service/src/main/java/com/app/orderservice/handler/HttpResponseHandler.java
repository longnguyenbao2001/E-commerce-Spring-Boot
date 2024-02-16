/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class HttpResponseHandler {

    public ResponseEntity<?> handleAcceptedRequest(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<?> handleAcceptedRequest(Object object) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(object);
    }

    public ResponseEntity<?> handleAcceptedRequest(List<Object> list) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }
}
