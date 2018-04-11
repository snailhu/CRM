package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.enums.StockChangeType;

public class StockChangeItem{

    private Long stockChangeId;

    private StockChangeType stockChangeType;

    private Long productId;

    private Integer count;

    private Integer realCount;

    private Long repoId;

    public Long getStockChangeId() {
        return stockChangeId;
    }

    public void setStockChangeId(Long stockChangeId) {
        this.stockChangeId = stockChangeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public StockChangeType getStockChangeType() {
        return stockChangeType;
    }

    public StockChangeItem setStockChangeType(StockChangeType stockChangeType) {
        this.stockChangeType = stockChangeType;
        return this;
    }

    public Long getRepoId() {
        return repoId;
    }

    public StockChangeItem setRepoId(Long repoId) {
        this.repoId = repoId;
        return this;
    }

    public StockChangeLog makeLog() {
        StockChangeLog log = new StockChangeLog();
        log.setCount(getRealCount());
        log.setProductId(getProductId());
        log.setRepoId(getRepoId());
        log.setStockChangeId(getStockChangeId());
        log.setStockChangeType(getStockChangeType());
        return log;
    }

    public Integer getRealCount() {
        return realCount;
    }

    public StockChangeItem setRealCount(Integer realCount) {
        this.realCount = realCount;
        return this;
    }
}