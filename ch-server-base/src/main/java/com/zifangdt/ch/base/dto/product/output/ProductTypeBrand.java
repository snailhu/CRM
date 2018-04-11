package com.zifangdt.ch.base.dto.product.output;

import java.util.List;

public class ProductTypeBrand {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public ProductTypeBrand setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public List<String> getBrands() {
        return brands;
    }

    public ProductTypeBrand setBrands(List<String> brands) {
        this.brands = brands;
        return this;
    }

    private List<String> brands;
}
