package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.product.bo.InStockQueryBo;

import java.util.List;

public interface InStockChangeMapper extends BaseMapper<InStockChange>{
    Long countFor(String format);

    List<InStockChange> getByPurchase(Long purchaseId);

    List<InStockChange> findBy(InStockQueryBo bo);

    List<InStockChange> findForPurchase(Long purchaseId);
}