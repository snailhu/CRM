package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.PurchaseItem;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

public class PurchaseItemDto implements ToEntity<PurchaseItem> {
    public Long getProductId() {
        return productId;
    }

    public PurchaseItemDto setId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public PurchaseItemDto setCount(Integer count) {
        this.count = count;
        return this;
    }

    @NotNull
    private Long productId;
    @NotNull
    private Integer count;

    @Override
    public PurchaseItem convertTo() {
        PurchaseItem purchaseItem = new PurchaseItem();
        BeanUtils.copyProperties(this, purchaseItem);
        return purchaseItem;
    }
}
