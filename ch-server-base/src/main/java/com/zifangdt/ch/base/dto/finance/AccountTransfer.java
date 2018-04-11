package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@Table(name = "biz_account_transfer")
public class AccountTransfer extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 7664790802847993793L;

    @NotNull
    private Long inAccountId;
    @NotNull
    private Long outAccountId;
    @NotNull
    private BigDecimal money;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long createId;
    private Date createTime;
    private String remark;
    @VerboseProperty(target = JsonPropertyTarget.FILE)
    private List<String> attachments;
    @NotNull
    private Date handleDate;

    @Transient
    private String inAccountName;
    @Transient
    private String outAccountName;

    public String getInAccountName() {
        return inAccountName;
    }

    public void setInAccountName(String inAccountName) {
        this.inAccountName = inAccountName;
    }

    public String getOutAccountName() {
        return outAccountName;
    }

    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName;
    }

    public Long getInAccountId() {
        return inAccountId;
    }

    public void setInAccountId(Long inAccountId) {
        this.inAccountId = inAccountId;
    }

    public Long getOutAccountId() {
        return outAccountId;
    }

    public void setOutAccountId(Long outAccountId) {
        this.outAccountId = outAccountId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }
}
