/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.entity;

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
@Table(name = "variant_values")
@NamedQueries({
    @NamedQuery(name = "VariantValues.findAll", query = "SELECT v FROM VariantValues v")})
public class VariantValues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "variantValuesList", fetch = FetchType.LAZY)
    private List<Variants> variantsList;
    @JoinColumn(name = "variant_label_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VariantLabels variantLabels;

    public VariantValues() {
    }

    public VariantValues(Long id) {
        this.id = id;
    }

    public VariantValues(Long id, String name) {
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

    public List<Variants> getVariantsList() {
        return variantsList;
    }

    public void setVariantsList(List<Variants> variantsList) {
        this.variantsList = variantsList;
    }

    public VariantLabels getVariantLabels() {
        return variantLabels;
    }

    public void setVariantLabels(VariantLabels variantLabels) {
        this.variantLabels = variantLabels;
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
        if (!(object instanceof VariantValues)) {
            return false;
        }
        VariantValues other = (VariantValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.commondataservice.entity.VariantValues[ id=" + id + " ]";
    }

}
