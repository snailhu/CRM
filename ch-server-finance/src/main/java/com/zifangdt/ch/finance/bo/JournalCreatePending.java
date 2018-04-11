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
public class JournalCreatePending extends AbstractJournalCreate {
    @NotNull
    private Date plannedDate;
    @NotNull
    @DecimalMin(value = "0", message = "不能小于0")
    private BigDecimal plannedMoney;

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public BigDecimal getPlannedMoney() {
        return plannedMoney;
    }

    public void setPlannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
    }

    @Override
    public Set<JournalStatus> supportedStatuses() {
        return EnumSet.of(JournalStatus.PENDING_EXPENSE, JournalStatus.PENDING_REVENUE);
    }
}
