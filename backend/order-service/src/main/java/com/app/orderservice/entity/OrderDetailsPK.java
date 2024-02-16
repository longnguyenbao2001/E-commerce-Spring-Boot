/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author user
 */
@Embeddable
public class OrderDetailsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "order_id")
    private long orderId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private long productId;
    @Basic(optional = false)
    @Column(name = "variant_id")
    private long variantId;

    public OrderDetailsPK() {
    }

    public OrderDetailsPK(long orderId, long productId, long variantId) {
        this.orderId = orderId;
        this.productId = productId;
        this.variantId = variantId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVariantId() {
        return variantId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderId;
        hash += (int) productId;
        hash += (int) variantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetailsPK)) {
            return false;
        }
        OrderDetailsPK other = (OrderDetailsPK) object;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        if (this.variantId != other.variantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.orderservice.entity.OrderDetailsPK[ orderId=" + orderId + ", productId=" + productId + ", variantId=" + variantId + " ]";
    }

}
