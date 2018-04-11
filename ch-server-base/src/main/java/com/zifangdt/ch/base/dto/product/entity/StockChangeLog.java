package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.StockChangeType;

import javax.persistence.Table;

@Table(name = "stock_change_log")
public class StockChangeLog extends AuditEntity<Long>{

    private Long stockChangeId;

    private StockChangeType stockChangeType;

    private Long productId;

    private Integer count;

    private Long repoId;

    public Integer getBeforeNumber() {
        return beforeNumber;
    }

    public StockChangeLog setBeforeNumber(Integer beforeNumber) {
        this.beforeNumber = beforeNumber;
        return this;
    }

    public Integer getAfterNumber() {
        return afterNumber;
    }

    public StockChangeLog setAfterNumber(Integer afterNumber) {
        this.afterNumber = afterNumber;
        return this;
    }

    private Integer beforeNumber;
    private Integer afterNumber;
    public Long getStockChangeId() {
        return stockChangeId;
    }

    public StockChangeLog setStockChangeId(Long stockChangeId) {
        this.stockChangeId = stockChangeId;
        return this;
    }

    public StockChangeType getStockChangeType() {
        return stockChangeType;
    }

    public StockChangeLog setStockChangeType(StockChangeType stockChangeType) {
        this.stockChangeType = stockChangeType;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public StockChangeLog setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public StockChangeLog setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Long getRepoId() {
        return repoId;
    }

    public StockChangeLog setRepoId(Long repoId) {
        this.repoId = repoId;
        return this;
    }
}