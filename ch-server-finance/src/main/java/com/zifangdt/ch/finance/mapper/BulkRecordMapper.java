package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.finance.BulkRecord;
import com.zifangdt.ch.finance.bo.BulkRecordListQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@Repository
public interface BulkRecordMapper extends BaseMapper<BulkRecord> {
    List<BulkRecord> getList(BulkRecordListQuery bo);
}
