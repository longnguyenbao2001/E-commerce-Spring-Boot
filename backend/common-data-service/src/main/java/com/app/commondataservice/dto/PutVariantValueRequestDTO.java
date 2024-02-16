/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author user
 */
public class PutVariantValueRequestDTO {

    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Digits(integer = 15, fraction = 0)
    private Long variantLabelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVariantLabelId() {
        return variantLabelId;
    }

    public void setVariantLabelId(Long variantLabelId) {
        this.variantLabelId = variantLabelId;
    }
}
