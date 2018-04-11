package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.dto.product.output.ProjectOutStockProduct;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/30.
 */
public class InventoryCostBo {
    private BigDecimal giftCost;
    private BigDecimal designCost;
    private BigDecimal extendServiceCost;
    private BigDecimal totalCost;
    private List<ProjectOutStockProduct> materialCosts;

    public BigDecimal getGiftCost() {
        return giftCost;
    }

    public void setGiftCost(BigDecimal giftCost) {
        this.giftCost = giftCost;
    }

    public BigDecimal getDesignCost() {
        return designCost;
    }

    public void setDesignCost(BigDecimal designCost) {
        this.designCost = designCost;
    }

    public BigDecimal getExtendServiceCost() {
        return extendServiceCost;
    }

    public void setExtendServiceCost(BigDecimal extendServiceCost) {
        this.extendServiceCost = extendServiceCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<ProjectOutStockProduct> getMaterialCosts() {
        return materialCosts;
    }

    public void setMaterialCosts(List<ProjectOutStockProduct> materialCosts) {
        this.materialCosts = materialCosts;
    }
}
