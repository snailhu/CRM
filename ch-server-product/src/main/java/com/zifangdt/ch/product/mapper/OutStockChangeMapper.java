package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.OutStockChange;
import com.zifangdt.ch.product.bo.OutStockQueryBo;

import java.util.List;

public interface OutStockChangeMapper extends BaseMapper<OutStockChange>{
    Long countFor(String format);

    List<OutStockChange> findBy(OutStockQueryBo bo);

    List<OutStockChange> findForProject(Long projectId);

    List<OutStockChange> findPreOut();
}