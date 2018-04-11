package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;

public class StockProductDto implements ToEntity<StockChangeItem>{

    public Long getProductId() {
        return productId;
    }

    public StockProductDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public StockProductDto setCount(Integer count) {
        this.count = count;
        return this;
    }

    private Long productId;

    private Integer count;

    @Override
    public StockChangeItem convertTo() {
        return null;
    }
}
