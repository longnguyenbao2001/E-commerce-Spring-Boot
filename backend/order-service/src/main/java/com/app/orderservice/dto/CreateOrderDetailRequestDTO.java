/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author user
 */
public class CreateOrderDetailRequestDTO {

    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Long productId;
    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Long variantId;
    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
