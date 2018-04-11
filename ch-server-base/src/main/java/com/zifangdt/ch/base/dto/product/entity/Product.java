package com.zifangdt.ch.base.dto.product.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.ProductUnit;
import com.zifangdt.ch.base.validation.CategoryExist;
import com.zifangdt.ch.base.validation.ValidOrder;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "product")
@GroupSequence({Product.class, ValidOrder.Second.class})
public class Product extends AuditEntity<Long> implements Serializable{

    private String[] img;

    @JsonProperty("imgUrls")
    public List<String> getImageUrls() {
        if (getImg() != null && getImg().length != 0) {
            return Arrays.asList(getImg()).stream()
                    .map(fileId -> "common/files/" + fileId + "?view=true")
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @NotNull
    private String name;

    private String number;

    @NotNull
    @CategoryExist(groups = ValidOrder.Second.class)
    private Long categoryId;

    @NotNull
    private Long productTypeId;

    private String brand;

    private String type;

    private BigDecimal price; // 报价
    private BigDecimal cost; // 成本

    @NotNull
    private Boolean isActive;

    private String description;

    private String remark;

    // 库存报警
    private Integer alertNumber;

    // 已销售量
    @Transient
    private Long soldNumber;

    // 签约库存
    @Transient
    private Long signedNumber;

    @Transient
    private Category category;

    @Transient
    private ProductType productType;

    @NotNull
    @NamedProperty
    private ProductUnit unitType;

    // 创建产品的库存，存放在默认仓库中
    @Transient
    @Min(value = 0)
    private Integer repoNumber;

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Integer getRepoNumber() {
        return repoNumber;
    }

    public void setRepoNumber(Integer repoNumber) {
        this.repoNumber = repoNumber;
    }

    public Integer getAlertNumber() {
        return alertNumber;
    }

    public Product setAlertNumber(Integer alertNumber) {
        this.alertNumber = alertNumber;
        return this;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public Product setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
        return this;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductUnit getUnitType() {
        return unitType;
    }

    public Product setUnitType(ProductUnit unitType) {
        this.unitType = unitType;
        return this;
    }

    public Long getSoldNumber() {
        return soldNumber;
    }

    public Product setSoldNumber(Long soldNumber) {
        this.soldNumber = soldNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getSignedNumber() {
        return signedNumber;
    }

    public void setSignedNumber(Long signedNumber) {
        this.signedNumber = signedNumber;
    }
}