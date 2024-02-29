/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ListProductDTO {

    private List<ProductDTO> listProducts;
    private Integer totalCount;

    public ListProductDTO() {
        this.listProducts = new ArrayList<>();
    }

    public List<ProductDTO> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ProductDTO> listProducts) {
        this.listProducts = listProducts;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
