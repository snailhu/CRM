package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.StockChangeLog;
import com.zifangdt.ch.base.dto.product.output.ProjectOutStockProduct;
import com.zifangdt.ch.base.dto.product.output.StockChangeLogBrief;
import com.zifangdt.ch.product.bo.StockChangeLogQueryBo;

import java.util.List;

public interface StockChangeLogMapper extends BaseMapper<StockChangeLog>{
    List<StockChangeLogBrief> getList(StockChangeLogQueryBo bo);

    List<StockChangeLogBrief> getListForProduct(Long productId);

    List<ProjectOutStockProduct> getForProject(Long projectId);

    Long getOutNumberForProduct(Long productId);
}