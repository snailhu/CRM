package com.zifangdt.ch.base.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.product.entity.RepoProduct;
import com.zifangdt.ch.base.enums.product.RepoType;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(value = {"price", "cost"}, allowGetters = true)
public class RepoProductDetailDto1 extends RepoProduct {

    private String productName;
    private String productNumber;
    private String repoName;
    private BigDecimal productPrice;
    private BigDecimal productCost;
    private String categoryName;
    private List<String> img;
    private RepoType repoType;
    private Integer alertNumber;

    public BigDecimal getPrice() {
        return productPrice;
    }

    public BigDecimal getCost() {
        return productCost;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public RepoProductDetailDto1 setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public RepoProductDetailDto1 setProductNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public List<String> getImg() {
        return img;
    }

    public RepoProductDetailDto1 setImg(List<String> img) {
        this.img = img;
        return this;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public void setRepoType(RepoType repoType) {
        this.repoType = repoType;
    }

    public Integer getAlertNumber() {
        return alertNumber;
    }

    public void setAlertNumber(Integer alertNumber) {
        this.alertNumber = alertNumber;
    }
}
