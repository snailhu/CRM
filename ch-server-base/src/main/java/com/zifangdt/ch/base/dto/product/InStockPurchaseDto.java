package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.enums.product.InStockType;

import javax.validation.constraints.NotNull;

/**
 * 采购入库
 */
public class InStockPurchaseDto extends InStockChangeDto implements ToEntity<InStockChange>{

    @NotNull
    private Long purchaseId;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public InStockPurchaseDto setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    @Override
    public InStockChange convertTo() {
        InStockChange inStockChange = super.convertTo();
        inStockChange.setInStockType(InStockType.Purchase);
        inStockChange.setPurchaseId(getPurchaseId());
        return inStockChange;
    }
}
