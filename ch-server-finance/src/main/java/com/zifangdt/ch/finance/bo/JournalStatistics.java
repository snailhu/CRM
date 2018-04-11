package com.zifangdt.ch.finance.bo;

import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public class JournalStatistics {
    private BigDecimal pendingRevenue;
    private BigDecimal doneRevenue;
    private BigDecimal pendingExpense;
    private BigDecimal doneExpense;

    public BigDecimal getPendingRevenue() {
        return pendingRevenue;
    }

    public void setPendingRevenue(BigDecimal pendingRevenue) {
        this.pendingRevenue = pendingRevenue;
    }

    public BigDecimal getDoneRevenue() {
        return doneRevenue;
    }

    public void setDoneRevenue(BigDecimal doneRevenue) {
        this.doneRevenue = doneRevenue;
    }

    public BigDecimal getPendingExpense() {
        return pendingExpense;
    }

    public void setPendingExpense(BigDecimal pendingExpense) {
        this.pendingExpense = pendingExpense;
    }

    public BigDecimal getDoneExpense() {
        return doneExpense;
    }

    public void setDoneExpense(BigDecimal doneExpense) {
        this.doneExpense = doneExpense;
    }

    public JournalStatistics() {
    }

    public JournalStatistics(BigDecimal pendingRevenue, BigDecimal doneRevenue, BigDecimal pendingExpense, BigDecimal doneExpense) {
        this.pendingRevenue = pendingRevenue;
        this.doneRevenue = doneRevenue;
        this.pendingExpense = pendingExpense;
        this.doneExpense = doneExpense;
    }
}
