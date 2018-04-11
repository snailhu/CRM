package com.zifangdt.ch.finance.service;

import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.dto.finance.BulkRecord;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.finance.bo.BulkRecordListQuery;
import com.zifangdt.ch.finance.mapper.BulkRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@Service
public class BulkRecordService extends BaseService<BulkRecord, Long> {
    @Autowired
    private BulkRecordMapper bulkRecordMapper;

    public PageResultBo<BulkRecord> list(BulkRecordListQuery bo) {
        return PageResultBo.of(bo, bulkRecordMapper::getList);
    }

}
