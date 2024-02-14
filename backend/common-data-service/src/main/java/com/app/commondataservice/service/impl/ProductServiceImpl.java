/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.ProductRepository;
import com.app.commondataservice.dto.CreateProductRequestDTO;
import com.app.commondataservice.dto.CreateVariantValueRequestDTO;
import com.app.commondataservice.dto.CreateVariantRequestDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.entity.Categories;
import com.app.commondataservice.entity.VariantLabels;
import com.app.commondataservice.entity.VariantValues;
import com.app.commondataservice.entity.Variants;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import com.app.commondataservice.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.commondataservice.dao.VariantValueRepository;
import com.app.commondataservice.dao.VariantRepository;
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
    private VariantRepository variantRepository;
    
    @Autowired
    private VariantValueRepository variantValueRepository;
    
    @Autowired
    private DTOConverter productConverter;
    
    @Override
    public Products getProductByProductId(Long productId)
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
            res.add(productConverter.convertProductToDTO(product));
        }
        
        return res;
    }
    
    @Override
    public ProductDetailDTO getProductDetail(Long productId) throws DataNotFoundException {
        return productConverter.convertProductDetailToDTO(this.getProductByProductId(productId));
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
    @Transactional
    public VariantValues createVariantValue(
            CreateVariantValueRequestDTO createVariantValueRequestDTO) {
        VariantValues variantValues = new VariantValues();
        
        variantValues.setName(createVariantValueRequestDTO.getName());
        
        VariantLabels variantLabels = new VariantLabels(createVariantValueRequestDTO.getVariantLabelId());
        variantValues.setVariantLabels(variantLabels);
        
        return variantValueRepository.save(variantValues);
    }
    
    @Override
    @Transactional
    public Variants createVariantData(CreateVariantRequestDTO createVariantRequestDTO, Long refUserId) {
        Products product = new Products(createVariantRequestDTO.getProductId());
        
        Variants variant = new Variants();
        variant.setProducts(product);
        variant.setUnitPrice(createVariantRequestDTO.getUnitPrice());
        variant.setQuantity(createVariantRequestDTO.getQuantity());
        
        List<VariantValues> listVariantValues = new ArrayList<>();
        
        for (CreateVariantValueRequestDTO createVariantValueRequestDTO : createVariantRequestDTO.getListVariantValues()) {
            VariantValues variantValues = this.createVariantValue(createVariantValueRequestDTO);
            listVariantValues.add(variantValues);
        }
        
        variant.setVariantValuesList(listVariantValues);
        
        return variant;
    }
    
    @Override
    @Transactional
    public void createVariant(CreateVariantRequestDTO createVariantRequestDTO, Long refUserId) {
        Variants variant = this.createVariantData(createVariantRequestDTO, refUserId);
        List<VariantValues> listVariantValues = variant.getVariantValuesList();
        
        variant = variantRepository.save(variant);
        for (VariantValues variantValues : listVariantValues) {
            variantValues.setVariantsList(new ArrayList<>());
            variantValues.getVariantsList().add(variant);
            
            variantValueRepository.save(variantValues);
        }
    }
    
    @Override
    public void putProduct(PutProductRequestDTO putProductRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.getProductByProductId(putProductRequestDTO.getId());
        
        product.setName(putProductRequestDTO.getName());
        product.setDescription(putProductRequestDTO.getDescription());
        
        Categories category = new Categories(putProductRequestDTO.getCategoryId());
        product.setCategories(category);
        
        productRepository.save(product);
    }
    
    @Override
    public void deleteProduct(Long productId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.getProductByProductId(productId);
        
        productRepository.delete(product);
    }
}
