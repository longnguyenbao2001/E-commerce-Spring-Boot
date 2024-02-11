/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import com.app.commondataservice.entity.ProductVariantLabels;
import java.math.BigDecimal;

/**
 *
 * @author user
 */
public class ProductVariantDTO {

    private String name;
    private BigDecimal unitPrice;
    private int quantity;
    private ProductVariantLabelDTO label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ProductVariantLabelDTO getLabel() {
        return label;
    }

    public void setLabel(ProductVariantLabelDTO label) {
        this.label = label;
    }

}
