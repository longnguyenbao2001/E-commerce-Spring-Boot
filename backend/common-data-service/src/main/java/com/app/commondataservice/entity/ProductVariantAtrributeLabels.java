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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "product_variant_atrribute_labels")
@NamedQueries({
    @NamedQuery(name = "ProductVariantAtrributeLabels.findAll", query = "SELECT p FROM ProductVariantAtrributeLabels p")})
public class ProductVariantAtrributeLabels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "productVariantAtrributeLabels", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductVariantAtrributeValues> productVariantAtrributeValuesList;

    public ProductVariantAtrributeLabels() {
    }

    public ProductVariantAtrributeLabels(Long id) {
        this.id = id;
    }

    public ProductVariantAtrributeLabels(Long id, String name) {
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

    public List<ProductVariantAtrributeValues> getProductVariantAtrributeValuesList() {
        return productVariantAtrributeValuesList;
    }

    public void setProductVariantAtrributeValuesList(List<ProductVariantAtrributeValues> productVariantAtrributeValuesList) {
        this.productVariantAtrributeValuesList = productVariantAtrributeValuesList;
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
        if (!(object instanceof ProductVariantAtrributeLabels)) {
            return false;
        }
        ProductVariantAtrributeLabels other = (ProductVariantAtrributeLabels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.commondataservice.entity.ProductVariantAtrributeLabels[ id=" + id + " ]";
    }

}
