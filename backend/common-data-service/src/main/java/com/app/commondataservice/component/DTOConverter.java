/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.component;

import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductVariantDTO;
import com.app.commondataservice.dto.ProductVariantLabelDTO;
import com.app.commondataservice.dto.UserDTO;
import com.app.commondataservice.entity.ProductVariantLabels;
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

    public UserDTO convertUserToUserDTO(Users user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    public ProductVariantLabelDTO convertProductVariantLabelToProductVariantLabelDTO(ProductVariantLabels productVariantLabels) {
        ProductVariantLabelDTO productVariantLabelDTO = modelMapper.map(productVariantLabels, ProductVariantLabelDTO.class);

        return productVariantLabelDTO;
    }

    public ProductVariantDTO convertProductVariantToProductVariantDTO(ProductVariants productVariant) {
        ProductVariantDTO productVariantDTO = modelMapper.map(productVariant, ProductVariantDTO.class);
        productVariantDTO.setLabel(this.convertProductVariantLabelToProductVariantLabelDTO(productVariant.getProductVariantLabels()));

        return productVariantDTO;
    }

    public ProductDTO convertProductToProductDTO(Products product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setSeller(this.convertUserToUserDTO(product.getUsers()));

        List<ProductVariantDTO> variants = new ArrayList<>();
        for (ProductVariants productVariant : product.getProductVariantsList()) {
            variants.add(this.convertProductVariantToProductVariantDTO(productVariant));
        }
        productDTO.setVariants(variants);

        return productDTO;
    }
}
