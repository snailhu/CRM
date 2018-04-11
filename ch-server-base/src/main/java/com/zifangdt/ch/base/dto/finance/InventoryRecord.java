package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.dto.BaseEntity;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/2/7.
 */
@Table(name = "biz_inventory_record")
public class InventoryRecord extends BaseEntity<Long> {
    private Long projectId;
    private InventoryItem[] revenueDetail;
    private InventoryItem[] expenseDetail;
    private BigDecimal materialCost;
    private BigDecimal otherCost;
    private Long createId;
    private Date createTime;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public InventoryItem[] getRevenueDetail() {
        return revenueDetail;
    }

    public void setRevenueDetail(InventoryItem[] revenueDetail) {
        this.revenueDetail = revenueDetail;
    }

    public InventoryItem[] getExpenseDetail() {
        return expenseDetail;
    }

    public void setExpenseDetail(InventoryItem[] expenseDetail) {
        this.expenseDetail = expenseDetail;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
