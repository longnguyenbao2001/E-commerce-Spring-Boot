/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.controller;

import com.app.commondataservice.dto.GetVariantsQuantityResponseDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.exception.DataNotFoundException;
import com.app.commondataservice.handler.HttpErrorResponseHandler;
import com.app.commondataservice.handler.HttpResponseHandler;
import com.app.commondataservice.service.VariantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/variants")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @Autowired
    private HttpErrorResponseHandler httpErrorResponseHandler;

    @Autowired
    private HttpResponseHandler httpResponseHandler;

    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getVariantsQuantity(@RequestParam List<Long> variantsIds) {
        try {
            List<GetVariantsQuantityResponseDTO> res = variantService.getVariantsQuantity(variantsIds);

            return httpResponseHandler.handleAcceptedRequest(res);
        } catch (DataNotFoundException e) {
            return httpErrorResponseHandler.handleBadRequest(env.getProperty("mes.data.notFound"));
        } catch (Exception e) {
            return httpErrorResponseHandler.handleInternalServerError(e.getMessage());
        }
    }
}
