/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.ProductRepository;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import com.app.commondataservice.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author user
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DTOConverter productConverter;

    @Override
    public List<ProductDTO> getListProduct(String keyword) {
        List<ProductDTO> res = new ArrayList<>();
        for (Products product : productRepository.findByNameContaining(keyword)) {
            res.add(productConverter.convertProductToDTO(product));
        }

        return res;
    }

    @Override
    public ProductDetailDTO getProductDetail(Long productId) throws DataNotFoundException {
        Optional<Products> opProduct = productRepository.findById(productId);
        if (!opProduct.isPresent()) {
            throw new DataNotFoundException();
        }

        return productConverter.convertProductDetailToDTO(opProduct.get());
    }

}
