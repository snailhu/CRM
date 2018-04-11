package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.finance.InventoryRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by 袁兵 on 2018/2/7.
 */
@Repository
public interface InventoryRecordMapper extends BaseMapper<InventoryRecord> {
    InventoryRecord getByProjectId(Long projectId);
}
