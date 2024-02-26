/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.controller;

import com.app.orderservice.dto.AuthUserDTO;
import com.app.orderservice.dto.CreateOrderDetailRequestDTO;
import com.app.orderservice.dto.DeleteOrderDetailRequestDTO;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.dto.PutOrderDetailRequestDTO;
import com.app.orderservice.exception.DataNotFoundException;
import com.app.orderservice.exception.UserNotFoundException;
import com.app.orderservice.handler.HttpErrorResponseHandler;
import com.app.orderservice.handler.HttpResponseHandler;
import com.app.orderservice.service.CallApiService;
import com.app.orderservice.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        try {
            List<OrderDTO> res = orderService.getMyOrders(authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        try {
            orderService.createOrder(authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long orderId) {
        try {
            List<OrderDetailDTO> res = orderService.getOrderDetails(orderId);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<?> createOrderDetails(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long orderId,
            @Valid @RequestBody List<CreateOrderDetailRequestDTO> listCreateOrderDetailRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            orderService.createOrderDetails(orderId, listCreateOrderDetailRequestDTO);

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> putOrderDetails(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long orderId,
            @Valid @RequestBody List<PutOrderDetailRequestDTO> listPutOrderDetailRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            orderService.putOrderDetails(orderId, listPutOrderDetailRequestDTO);

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<?> deleteOrderDetails(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @PathVariable Long orderId,
            @Valid @RequestBody List<DeleteOrderDetailRequestDTO> listDeleteOrderDetailRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            orderService.deleteOrderDetails(orderId, listDeleteOrderDetailRequestDTO);

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
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
