/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.controller;

import com.app.orderservice.dto.AuthUserDTO;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.exception.UserNotFoundException;
import com.app.orderservice.handler.HttpErrorResponseHandler;
import com.app.orderservice.handler.HttpResponseHandler;
import com.app.orderservice.service.CallApiService;
import com.app.orderservice.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CallApiService callApiService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @GetMapping("/myorders")
    public ResponseEntity<?> getMyOrdersList(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        try {
            List<OrderDTO> res = orderService.getMyOrdersList(authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/myorders/{orderId}")
    public ResponseEntity<?> getOrderDetails(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long orderId) {
        try {
            List<OrderDetailDTO> res = orderService.getOrderDetails(orderId);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestHeader("Authorization") String token) {
        try {
            AuthUserDTO res = callApiService.authenticate(token);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (UserNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notFound"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
