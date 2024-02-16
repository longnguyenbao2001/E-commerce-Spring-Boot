/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author user
 */
public class PutVariantRequestDTO {

    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 15, fraction = 0)
    private BigDecimal unitPrice;
    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Integer quantity;
    @NotNull
    private List<PutVariantValueRequestDTO> listVariantValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<PutVariantValueRequestDTO> getListVariantValues() {
        return listVariantValues;
    }

    public void setListVariantValues(List<PutVariantValueRequestDTO> listVariantValues) {
        this.listVariantValues = listVariantValues;
    }
}
