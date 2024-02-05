/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.dao.ProductRepository;
import com.app.commondataservice.entity.Products;
import org.springframework.stereotype.Service;
import com.app.commondataservice.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author user
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Products> getProducts() {
        return productRepository.findAll();
    }

}
