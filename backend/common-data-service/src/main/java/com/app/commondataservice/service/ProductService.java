/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.ProductDTO;
import java.util.List;
import com.app.commondataservice.entity.Products;

/**
 *
 * @author user
 */
public interface ProductService {

    public List<ProductDTO> getProducts(String keyword);
}
