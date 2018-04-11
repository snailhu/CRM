package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.product.InStockStatus;
import com.zifangdt.ch.base.enums.product.InStockType;

import javax.persistence.Table;

/**
 * 入库单
 */
@Table(name = "in_stock_change")
public class InStockChange extends AuditEntity<Long>{

    private Long repoId;

    private InStockType inStockType;

    private Long purchaseId;

    private String stockNumber;

    private Long projectId;

    private String relateName;

    private InStockStatus stockStatus;

    private StockChangeItem[] items;

    public Long getRepoId() {
        return repoId;
    }

    public InStockChange setRepoId(Long repoId) {
        this.repoId = repoId;
        return this;
    }

    public InStockType getInStockType() {
        return inStockType;
    }

    public InStockChange setInStockType(InStockType inStockType) {
        this.inStockType = inStockType;
        return this;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public InStockChange setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public InStockChange setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public InStockChange setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getRelateName() {
        return relateName;
    }

    public InStockChange setRelateName(String relateName) {
        this.relateName = relateName;
        return this;
    }

    public InStockStatus getStockStatus() {
        return stockStatus;
    }

    public InStockChange setStockStatus(InStockStatus stockStatus) {
        this.stockStatus = stockStatus;
        return this;
    }

    public StockChangeItem[] getItems() {
        return items;
    }

    public InStockChange setItems(StockChangeItem[] items) {
        this.items = items;
        return this;
    }
}