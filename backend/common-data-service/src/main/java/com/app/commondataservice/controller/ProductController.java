/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.controller;

import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.service.ProductService;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.handler.HttpErrorResponseHandler;
import com.app.commondataservice.handler.HttpResponseHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

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
}
