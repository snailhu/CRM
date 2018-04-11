package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.Category;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

public class CategoryCreateDto implements ToEntity<Category> {

    private Long createId;

    @NotEmpty
    private String name;

    private String description;

    private Long parentId;

    @Override
    public Category convertTo() {
        Category category = new Category();
        BeanUtils.copyProperties(this, category);
        return category;
    }

    public String getName() {
        return name;
    }

    public CategoryCreateDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryCreateDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public CategoryCreateDto setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Long getCreateId() {
        return createId;
    }

    public CategoryCreateDto setCreateId(Long createId) {
        this.createId = createId;
        return this;
    }
}
