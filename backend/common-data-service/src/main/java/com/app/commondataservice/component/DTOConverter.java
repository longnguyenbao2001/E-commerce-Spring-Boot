/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.component;

import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.dto.ProductVariantAttributeValueDTO;
import com.app.commondataservice.dto.ProductVariantDTO;
import com.app.commondataservice.dto.UserDTO;
import com.app.commondataservice.entity.ProductVariantAtrributeValues;
import com.app.commondataservice.entity.ProductVariants;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.Users;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertUserToDTO(Users user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    public ProductVariantAttributeValueDTO convertProductVariantAttributeValueToDTO(ProductVariantAtrributeValues productVariantAtrributeValues) {
        ProductVariantAttributeValueDTO productVariantAttributeValueDTO = modelMapper.map(productVariantAtrributeValues, ProductVariantAttributeValueDTO.class);
        productVariantAttributeValueDTO.setVariantLabel(productVariantAtrributeValues.getProductVariantAtrributeLabels().getName());

        return productVariantAttributeValueDTO;
    }

    public ProductVariantDTO convertProductVariantToDTO(ProductVariants productVariant) {
        ProductVariantDTO productVariantDTO = modelMapper.map(productVariant, ProductVariantDTO.class);

        List<ProductVariantAttributeValueDTO> listProductVariantAttributeValue = new ArrayList<>();
        for (ProductVariantAtrributeValues productVariantAtrributeValues : productVariant.getProductVariantAtrributeValuesList()) {
            listProductVariantAttributeValue.add(this.convertProductVariantAttributeValueToDTO(productVariantAtrributeValues));
        }
        productVariantDTO.setListProductVariantAttributeValue(listProductVariantAttributeValue);

        return productVariantDTO;
    }

    public ProductDetailDTO convertProductDetailToDTO(Products product) {
        ProductDetailDTO productDetailDTO = modelMapper.map(product, ProductDetailDTO.class);
        productDetailDTO.setSeller(this.convertUserToDTO(product.getUsers()));

        List<ProductVariantDTO> listProductVariant = new ArrayList<>();
        for (ProductVariants productVariant : product.getProductVariantsList()) {
            listProductVariant.add(this.convertProductVariantToDTO(productVariant));
        }
        productDetailDTO.setListProductVariant(listProductVariant);

        return productDetailDTO;
    }

    public ProductDTO convertProductToDTO(Products product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        return productDTO;
    }
}
