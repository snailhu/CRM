package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.bo.BatchUpdateBo;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/4/9.
 */
public class JournalBatchUpdateDone extends BatchUpdateBo {
    @NotNull
    private Long accountId;
    @NotNull
    private Date handleDate;
    @NotEmpty
    private List<BigDecimal> actualMoneys;
    private String remark;
    private List<String> attachments;
    private JournalStatus status;
    private Long currentUserId;
    private String currentUserName;

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public JournalStatus getStatus() {
        return status;
    }

    public void setStatus(JournalStatus status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public List<BigDecimal> getActualMoneys() {
        return actualMoneys;
    }

    public void setActualMoneys(List<BigDecimal> actualMoneys) {
        this.actualMoneys = actualMoneys;
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
