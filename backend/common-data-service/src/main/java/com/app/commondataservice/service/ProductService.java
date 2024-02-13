/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import java.util.List;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.exception.DataNotFoundException;

/**
 *
 * @author user
 */
public interface ProductService {

    public List<ProductDTO> getListProduct(String keyword);

    public ProductDetailDTO getProductDetail(Long productId)
            throws DataNotFoundException;
}
