package com.zifangdt.ch.base.dto.product.entity;

import java.math.BigDecimal;

public class PurchaseItem {

    private Long purchaseId;

    private Long productId;

    private Integer count;

    private BigDecimal price;

    private Product product;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PurchaseItem setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}