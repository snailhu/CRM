package com.zifangdt.ch.base.dto.product.output;

import com.zifangdt.ch.base.dto.product.entity.StockChangeLog;

public class StockChangeLogBrief extends StockChangeLog {
    private String productName;
    private String productNumber;
    private String repoName;
    private String stockNumber;
    private String categoryName;

    public String getProductName() {
        return productName;
    }

    public StockChangeLogBrief setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getRepoName() {
        return repoName;
    }

    public StockChangeLogBrief setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }


    public String getStockNumber() {
        return stockNumber;
    }

    public StockChangeLogBrief setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public StockChangeLogBrief setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public StockChangeLogBrief setProductNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }
}
