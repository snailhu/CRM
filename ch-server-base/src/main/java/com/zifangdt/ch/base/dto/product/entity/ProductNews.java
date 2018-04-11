package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.BaseNews;
import com.zifangdt.ch.base.enums.NewsType;
import com.zifangdt.ch.base.enums.ProductNewsType;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "product_news")
public class ProductNews extends BaseNews{
    @Transient
    private String productName;
    private Long productId;

    private ProductNewsType type;

    @Override
    protected NewsType getType() {
        return type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setType(ProductNewsType type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
