/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.controller;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.CreateProductRequestDTO;
import com.app.commondataservice.dto.CreateVariantRequestDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.dto.PutProductRequestDTO;
import com.app.commondataservice.service.ProductService;
import com.app.commondataservice.exception.DataNotFoundException;
import com.app.commondataservice.handler.HttpErrorResponseHandler;
import com.app.commondataservice.handler.HttpResponseHandler;
import com.app.commondataservice.service.CallApiService;
import exception.UserNotExistedException;
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
    private CallApiService callApiService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getListProduct(@RequestParam(
            required = false, defaultValue = "") String keyword) {
        try {
            List<ProductDTO> res = productService.getListProduct(keyword);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getListProduct(@PathVariable Long productId) {
        try {
            ProductDetailDTO res = productService.getProductDetail(productId);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @Valid @RequestBody CreateProductRequestDTO createProductRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            productService.createProduct(createProductRequestDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PostMapping("/variants/create")
    public ResponseEntity<?> createProductVariants(@AuthenticationPrincipal AuthUserDTO authUserDTO,
            @Valid @RequestBody CreateVariantRequestDTO createProductVariantRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            productService.createVariant(createProductVariantRequestDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> putProduct(@PathVariable Long productId,
            @AuthenticationPrincipal AuthUserDTO authUserDTO,
            @Valid @RequestBody PutProductRequestDTO putProductRequestDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return httpErrorResponseHandler.handleBadRequest(bindingResult);
            }

            putProductRequestDTO.setId(productId);
            productService.putProduct(putProductRequestDTO, authUserDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId,
            @AuthenticationPrincipal AuthUserDTO authUserDTO) {
        try {
            productService.deleteProduct(productId, authUserDTO, authUserDTO.getId());

            return httpResponseHandler.handleAcceptedRequest(env.getProperty("mes.success"));
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
