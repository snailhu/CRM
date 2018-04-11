package com.zifangdt.ch.base.dto.product;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import org.springframework.beans.BeanUtils;

public class StockChangeItemDetail extends StockChangeItem implements FromEntity<StockChangeItem, StockChangeItemDetail>{
    public String getProductName() {
        return productName;
    }

    public StockChangeItemDetail setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public StockChangeItemDetail setProductNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    private String productName;
    private String productNumber;

    @Override
    public StockChangeItemDetail convertFrom(StockChangeItem entity) {
        BeanUtils.copyProperties(entity, this);
        return this;
    }
}
