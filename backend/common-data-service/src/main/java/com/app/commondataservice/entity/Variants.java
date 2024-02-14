/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "variants")
@NamedQueries({
    @NamedQuery(name = "Variants.findAll", query = "SELECT v FROM Variants v")})
public class Variants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @JoinTable(name = "variants_variant_values", joinColumns = {
        @JoinColumn(name = "variant_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "variant_value_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<VariantValues> variantValuesList;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Products products;

    public Variants() {
    }

    public Variants(Long id) {
        this.id = id;
    }

    public Variants(Long id, BigDecimal unitPrice, int quantity) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<VariantValues> getVariantValuesList() {
        return variantValuesList;
    }

    public void setVariantValuesList(List<VariantValues> variantValuesList) {
        this.variantValuesList = variantValuesList;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
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
        if (!(object instanceof Variants)) {
            return false;
        }
        Variants other = (Variants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.commondataservice.entity.Variants[ id=" + id + " ]";
    }

}
