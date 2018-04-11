package com.zifangdt.ch.base.dto.customer;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.SalesClueFollowUpStatus;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/12/5.
 */
@Table(name = "biz_sales_clue_follow_up")
public class SalesClueFollowUp extends BaseEntity<Long> {
    private Long salesClueId;
    @VerboseProperty(target = JsonPropertyTarget.USER)
    private Long userId;
    @NamedProperty
    private SalesClueFollowUpStatus status;
    private Integer hasRead;
    private Date operateTime;
    private Long userOrganId;
    @Transient
    private String userOrganName;

    public Long getUserOrganId() {
        return userOrganId;
    }

    public void setUserOrganId(Long userOrganId) {
        this.userOrganId = userOrganId;
    }

    public String getUserOrganName() {
        return userOrganName;
    }

    public void setUserOrganName(String userOrganName) {
        this.userOrganName = userOrganName;
    }

    public Long getSalesClueId() {
        return salesClueId;
    }

    public void setSalesClueId(Long salesClueId) {
        this.salesClueId = salesClueId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SalesClueFollowUpStatus getStatus() {
        return status;
    }

    public void setStatus(SalesClueFollowUpStatus status) {
        this.status = status;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
