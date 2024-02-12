/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.controller;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.service.ProductService;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.handler.HttpErrorResponseHandler;
import com.app.commondataservice.handler.HttpResponseHandler;
import com.app.commondataservice.service.CallApiService;
import exception.UserNotExistedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CallApiService callApiService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(
            required = false, defaultValue = "") String keyword) {
        try {
            List<ProductDTO> res = productService.getProducts(keyword);

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
        } catch (UserNotExistedException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.user.notExisted"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
