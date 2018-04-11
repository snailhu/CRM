package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.enums.pair.JournalStatus;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public class JournalCreateDone extends AbstractJournalCreate {
    @NotNull
    private Date handleDate;
    @NotNull
    @DecimalMin(value = "0", message = "不能小于0")
    private BigDecimal actualMoney;
    @NotNull
    private Long accountId;

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public Set<JournalStatus> supportedStatuses() {
        return EnumSet.of(JournalStatus.DONE_EXPENSE, JournalStatus.DONE_REVENUE);
    }
}
