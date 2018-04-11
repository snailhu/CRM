package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.JournalBulkRecordStatus;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.validation.ConfiguredOption;

import java.util.Date;

/**
 * Created by 袁兵 on 2018/4/10.
 */
public class BulkRecordListQuery extends PageQueryBo {

    private JournalStatus applyType;
    private JournalBulkRecordStatus status;
    private Date operateDateStart;
    private Date operateDateEnd;
    @ConfiguredOption({ConfigType.journalTypes})
    private Long type;
    private Long createOrganId;
    private Long operateId;

    public JournalStatus getApplyType() {
        return applyType;
    }

    public void setApplyType(JournalStatus applyType) {
        this.applyType = applyType;
    }

    public JournalBulkRecordStatus getStatus() {
        return status;
    }

    public void setStatus(JournalBulkRecordStatus status) {
        this.status = status;
    }

    public Date getOperateDateStart() {
        return operateDateStart;
    }

    public void setOperateDateStart(Date operateDateStart) {
        this.operateDateStart = operateDateStart;
    }

    public Date getOperateDateEnd() {
        return operateDateEnd;
    }

    public void setOperateDateEnd(Date operateDateEnd) {
        this.operateDateEnd = operateDateEnd;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCreateOrganId() {
        return createOrganId;
    }

    public void setCreateOrganId(Long createOrganId) {
        this.createOrganId = createOrganId;
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }
}
