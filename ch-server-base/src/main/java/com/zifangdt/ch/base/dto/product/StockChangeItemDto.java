package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import com.zifangdt.ch.base.enums.StockChangeType;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StockChangeItemDto implements ToEntity<StockChangeItem>{
    public Long getProductId() {
        return productId;
    }

    public StockChangeItemDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getRealCount() {
        return realCount;
    }

    public StockChangeItemDto setRealCount(Integer realCount) {
        this.realCount = realCount;
        return this;
    }

    @NotNull
    private Long productId;

    @Min(0)
    private Integer realCount;

    @Override
    public StockChangeItem convertTo() {
        StockChangeItem stockChangeItem = new StockChangeItem();
        BeanUtils.copyProperties(this, stockChangeItem);
        stockChangeItem.setStockChangeType(StockChangeType.In);
        return stockChangeItem;
    }
}
