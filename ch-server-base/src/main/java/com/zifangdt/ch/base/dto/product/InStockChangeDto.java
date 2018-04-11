package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.enums.product.InStockStatus;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

public class InStockChangeDto implements ToEntity<InStockChange> {

    @NotNull
    private Long repoId;

    public Long getRepoId() {
        return repoId;
    }

    public InStockChangeDto setRepoId(Long repoId) {
        this.repoId = repoId;
        return this;
    }

    public List<StockChangeItemDto> getProducts() {
        return products;
    }

    public InStockChangeDto setProducts(List<StockChangeItemDto> products) {
        this.products = products;
        return this;
    }

    private List<StockChangeItemDto> products;

    @Override
    public InStockChange convertTo() {
        InStockChange inStockChange = new InStockChange();
        BeanUtils.copyProperties(this, inStockChange);
        inStockChange.setStockStatus(InStockStatus.InStock);
        return inStockChange;
    }
}
