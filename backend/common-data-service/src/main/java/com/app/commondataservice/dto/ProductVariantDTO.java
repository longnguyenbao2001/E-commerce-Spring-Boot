/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author user
 */
public class ProductVariantDTO {

    private int id;
    private BigDecimal unitPrice;
    private int quantity;
    private List<ProductVariantAttributeValueDTO> listProductVariantAttributeValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ProductVariantAttributeValueDTO> getListProductVariantAttributeValue() {
        return listProductVariantAttributeValue;
    }

    public void setListProductVariantAttributeValue(List<ProductVariantAttributeValueDTO> listProductVariantAttributeValue) {
        this.listProductVariantAttributeValue = listProductVariantAttributeValue;
    }

}
