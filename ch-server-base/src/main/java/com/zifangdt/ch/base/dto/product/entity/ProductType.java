package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "product_type")
public class ProductType extends AuditEntity<Long>{

    private String name;

    private String description;

    private Integer sort;

    @Transient
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public ProductType setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ProductType setCount(Integer count) {
        this.count = count;
        return this;
    }
}