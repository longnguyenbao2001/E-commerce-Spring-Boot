/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.ProductRepository;
import com.app.commondataservice.dto.CreateProductRequestDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.entity.Categories;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import com.app.commondataservice.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.PutProductRequestDTO;

/**
 *
 * @author user
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DTOConverter dtoConverter;

    @Override
    public Products findProductByProductId(Long productId)
            throws DataNotFoundException {
        Optional<Products> opProduct = productRepository.findById(productId);
        if (!opProduct.isPresent()) {
            throw new DataNotFoundException();
        }

        return opProduct.get();
    }

    @Override
    public List<ProductDTO> getListProduct(String keyword) {
        List<ProductDTO> res = new ArrayList<>();

        for (Products product : productRepository.findByNameContaining(keyword)) {
            res.add(dtoConverter.convertProductToDTO(product));
        }

        return res;
    }

    @Override
    public ProductDetailDTO getProductDetail(Long productId) throws DataNotFoundException {
        return dtoConverter.convertProductDetailToDTO(this.findProductByProductId(productId));
    }

    @Override
    public void createProduct(CreateProductRequestDTO createProductRequestDTO, Long refUserId) {
        Products product = new Products();
        product.setName(createProductRequestDTO.getName());
        product.setDescription(createProductRequestDTO.getDescription());

        Users user = new Users(refUserId);
        product.setUsers(user);

        Categories category = new Categories(createProductRequestDTO.getCategoryId());
        product.setCategories(category);

        productRepository.save(product);

    }

    @Override
    public void putProduct(PutProductRequestDTO putProductRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.findProductByProductId(putProductRequestDTO.getId());

        product.setName(putProductRequestDTO.getName());
        product.setDescription(putProductRequestDTO.getDescription());

        Categories category = new Categories(putProductRequestDTO.getCategoryId());
        product.setCategories(category);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.findProductByProductId(productId);

        productRepository.delete(product);
    }
}
