package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.enums.pair.FinanceBizType;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.enums.pair.TraderType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public abstract class AbstractJournalCreate {
    @NotNull
    private JournalStatus status;
    @NotNull
    private Long type;
    private FinanceBizType bizType;
    private Long bizId;
    @NotEmpty
    private Journal.JournalDetail[] details;
    @NotNull
    private TraderType traderType;
    @NotNull
    private Long traderId;
    private Long handleId;
    private String remark;
    private List<String> attachments;

    public abstract Set<JournalStatus> supportedStatuses();

    public JournalStatus getStatus() {
        return status;
    }

    public void setStatus(JournalStatus status) {
        this.status = status;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public FinanceBizType getBizType() {
        return bizType;
    }

    public void setBizType(FinanceBizType bizType) {
        this.bizType = bizType;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Journal.JournalDetail[] getDetails() {
        return details;
    }

    public void setDetails(Journal.JournalDetail[] details) {
        this.details = details;
    }

    public TraderType getTraderType() {
        return traderType;
    }

    public void setTraderType(TraderType traderType) {
        this.traderType = traderType;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Long getHandleId() {
        return handleId;
    }

    public void setHandleId(Long handleId) {
        this.handleId = handleId;
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
}
