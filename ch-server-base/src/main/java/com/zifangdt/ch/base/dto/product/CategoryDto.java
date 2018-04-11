package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.AbstractFromEntity;
import com.zifangdt.ch.base.dto.product.entity.Category;
import com.zifangdt.ch.base.dto.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto extends AbstractFromEntity<Category> {

    public CategoryDto(Category source) {
        super(source);
        this.setId(source.getId());
    }

    public String getName() {
        return name;
    }

    public CategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public CategoryDto setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentPath() {
        return parentPath;
    }

    public CategoryDto setParentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    private Long id;

    private String name;

    private String description;

    private Long parentId;

    private String parentPath;

    private List<Product> products;

    private Long count;

    public List<CategoryDto> getChildren() {
        return children;
    }

    public CategoryDto setChildren(List<CategoryDto> children) {
        this.children = children;
        return this;
    }

    private List<CategoryDto> children;

    public Long getId() {
        return id;
    }

    public CategoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public CategoryDto setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public CategoryDto setCount(Long count) {
        this.count = count;
        return this;
    }

    public void addChild(CategoryDto dto) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(dto);
    }
}
