package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.JournalBulkRecordStatus;
import com.zifangdt.ch.base.enums.pair.JournalStatus;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@Table(name = "biz_bulk_record")
public class BulkRecord extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 6271553695237872817L;

    @NamedProperty
    private JournalBulkRecordStatus status;
    @NamedProperty
    private JournalStatus applyType;
    @NamedProperty(target = JsonPropertyTarget.OPTION)
    private Long type;
    private BigDecimal sumMoney;
    private Long createId;
    private Date createTime;
    private Long createOrganId;
    private String createOrganName;
    private Long operateId;
    private Date operateTime;
    private Set<Long> journalIds;

    public JournalBulkRecordStatus getStatus() {
        return status;
    }

    public void setStatus(JournalBulkRecordStatus status) {
        this.status = status;
    }

    public JournalStatus getApplyType() {
        return applyType;
    }

    public void setApplyType(JournalStatus applyType) {
        this.applyType = applyType;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateOrganId() {
        return createOrganId;
    }

    public void setCreateOrganId(Long createOrganId) {
        this.createOrganId = createOrganId;
    }

    public String getCreateOrganName() {
        return createOrganName;
    }

    public void setCreateOrganName(String createOrganName) {
        this.createOrganName = createOrganName;
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Set<Long> getJournalIds() {
        return journalIds;
    }

    public void setJournalIds(Set<Long> journalIds) {
        this.journalIds = journalIds;
    }
}
