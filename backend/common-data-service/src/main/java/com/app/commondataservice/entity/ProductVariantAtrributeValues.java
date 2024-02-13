/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "product_variant_atrribute_values")
@NamedQueries({
    @NamedQuery(name = "ProductVariantAtrributeValues.findAll", query = "SELECT p FROM ProductVariantAtrributeValues p")})
public class ProductVariantAtrributeValues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinTable(name = "product_variants_product_variant_atrribute_values", joinColumns = {
        @JoinColumn(name = "product_variant_atrribute_value_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "product_variant_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductVariants> productVariantsList;
    @JoinColumn(name = "product_variant_atrribute_label_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ProductVariantAtrributeLabels productVariantAtrributeLabels;

    public ProductVariantAtrributeValues() {
    }

    public ProductVariantAtrributeValues(Long id) {
        this.id = id;
    }

    public ProductVariantAtrributeValues(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public List<ProductVariants> getProductVariantsList() {
        return productVariantsList;
    }

    public void setProductVariantsList(List<ProductVariants> productVariantsList) {
        this.productVariantsList = productVariantsList;
    }

    public ProductVariantAtrributeLabels getProductVariantAtrributeLabels() {
        return productVariantAtrributeLabels;
    }

    public void setProductVariantAtrributeLabels(ProductVariantAtrributeLabels productVariantAtrributeLabels) {
        this.productVariantAtrributeLabels = productVariantAtrributeLabels;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductVariantAtrributeValues)) {
            return false;
        }
        ProductVariantAtrributeValues other = (ProductVariantAtrributeValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.commondataservice.entity.ProductVariantAtrributeValues[ id=" + id + " ]";
    }

}
