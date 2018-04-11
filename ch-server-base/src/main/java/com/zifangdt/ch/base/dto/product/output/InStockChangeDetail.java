package com.zifangdt.ch.base.dto.product.output;

import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.product.StockChangeItemDetail;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class InStockChangeDetail extends InStockChange implements FromEntity<InStockChange, InStockChangeDetail>{

    private String repoName;
    private String createName;

    public String getRepoName() {
        return repoName;
    }

    public InStockChangeDetail setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public InStockChangeDetail setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public List<StockChangeItemDetail> getProducts() {
        return products;
    }

    public InStockChangeDetail setProducts(List<StockChangeItemDetail> products) {
        this.products = products;
        return this;
    }

    private List<StockChangeItemDetail> products;

    @Override
    public InStockChangeDetail convertFrom(InStockChange entity) {
        BeanUtils.copyProperties(entity, this);
        return this;
    }
}
