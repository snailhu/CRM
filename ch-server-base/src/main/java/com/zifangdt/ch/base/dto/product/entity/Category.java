package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "category")
public class Category extends AuditEntity<Long> {

    @NotEmpty
    private String name;

    private String description;

    private Long parentId;

    private String parentPath;

    @Transient
    private Long count;

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public Category setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentPath() {
        return parentPath;
    }

    public Category setParentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCount() {
        return count;
    }

    public Category setCount(Long count) {
        this.count = count;
        return this;
    }

}