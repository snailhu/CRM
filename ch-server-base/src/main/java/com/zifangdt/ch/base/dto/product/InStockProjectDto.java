package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.enums.product.InStockType;

import javax.validation.constraints.NotNull;

public class InStockProjectDto extends InStockChangeDto implements ToEntity<InStockChange>{
    @NotNull
    private Long projectId;

    @Override
    public InStockChange convertTo() {
        InStockChange inStockChange = super.convertTo();
        inStockChange.setInStockType(InStockType.MaterialReturn);
        inStockChange.setProjectId(getProjectId());
        return inStockChange;
    }

    public Long getProjectId() {
        return projectId;
    }

    public InStockProjectDto setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }
}
