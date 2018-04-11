package com.zifangdt.ch.base.dto.product.output;

import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.product.StockChangeItemDetail;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.dto.product.entity.OutStockChange;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 出库单详情
 */
public class OutStockChangeDetail extends OutStockChange implements FromEntity<OutStockChange, OutStockChangeDetail>{

    private String repoName;
    private String createName;

    public String getRepoName() {
        return repoName;
    }

    public OutStockChangeDetail setRepoName(String repoName) {
        this.repoName = repoName;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public OutStockChangeDetail setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public List<StockChangeItemDetail> getProducts() {
        return products;
    }

    public OutStockChangeDetail setProducts(List<StockChangeItemDetail> products) {
        this.products = products;
        return this;
    }

    private List<StockChangeItemDetail> products;

    @Override
    public OutStockChangeDetail convertFrom(OutStockChange entity) {
        BeanUtils.copyProperties(entity, this);
        return this;
    }
}
