/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import java.util.List;

/**
 *
 * @author user
 */
public class ProductDetailDTO {

    private String name;
    private String description;
    private List<VariantDTO> listVariants;
    private UserDTO seller;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<VariantDTO> getListVariants() {
        return listVariants;
    }

    public void setListVariants(List<VariantDTO> listVariants) {
        this.listVariants = listVariants;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

}
