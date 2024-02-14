/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.component;

import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.dto.VariantValueDTO;
import com.app.commondataservice.dto.VariantDTO;
import com.app.commondataservice.dto.UserDTO;
import com.app.commondataservice.entity.VariantValues;
import com.app.commondataservice.entity.Variants;
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

    public VariantValueDTO convertVariantValueToDTO(VariantValues variantValues) {
        VariantValueDTO variantValueDTO = modelMapper.map(variantValues, VariantValueDTO.class);
        variantValueDTO.setVariantLabel(variantValues.getVariantLabels().getName());

        return variantValueDTO;
    }

    public VariantDTO convertVariantToDTO(Variants variant) {
        VariantDTO variantDTO = modelMapper.map(variant, VariantDTO.class);

        List<VariantValueDTO> listVariantValues = new ArrayList<>();
        for (VariantValues variantValues : variant.getVariantValuesList()) {
            listVariantValues.add(this.convertVariantValueToDTO(variantValues));
        }
        variantDTO.setListVariantValues(listVariantValues);

        return variantDTO;
    }

    public ProductDetailDTO convertProductDetailToDTO(Products product) {
        ProductDetailDTO productDetailDTO = modelMapper.map(product, ProductDetailDTO.class);
        productDetailDTO.setSeller(this.convertUserToDTO(product.getUsers()));

        List<VariantDTO> listVariantDTO = new ArrayList<>();
        for (Variants variant : product.getVariantsList()) {
            listVariantDTO.add(this.convertVariantToDTO(variant));
        }
        productDetailDTO.setListVariants(listVariantDTO);

        return productDetailDTO;
    }

    public ProductDTO convertProductToDTO(Products product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        return productDTO;
    }
}
