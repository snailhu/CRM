package com.zifangdt.ch.finance.web;

import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.dto.finance.BulkRecord;
import com.zifangdt.ch.finance.bo.BulkRecordListQuery;
import com.zifangdt.ch.finance.service.BulkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@RestController
@RequestMapping("/bulkRecords")
public class BulkRecordController {
    @Autowired
    private BulkRecordService bulkRecordService;

    @GetMapping
    public PageResultBo<BulkRecord> list(BulkRecordListQuery bo) {
        return bulkRecordService.list(bo);
    }

    @GetMapping("/{id}")
    public BulkRecord getDetail(@PathVariable("id") Long id) {
        return bulkRecordService.get(id);
    }


}
