/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.CreateProductRequestDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.dto.PutProductRequestDTO;
import java.util.List;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.exception.DataNotFoundException;

/**
 *
 * @author user
 */
public interface ProductService {

    public Products findProductByProductId(Long productId)
            throws DataNotFoundException;

    public List<ProductDTO> getListProduct(String keyword);

    public ProductDetailDTO getProductDetail(Long productId)
            throws DataNotFoundException;

    public void createProduct(CreateProductRequestDTO createProductRequestDTO, Long refUserId);

    public void putProduct(PutProductRequestDTO putProductRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException;

    public void deleteProduct(Long productId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException;
}
