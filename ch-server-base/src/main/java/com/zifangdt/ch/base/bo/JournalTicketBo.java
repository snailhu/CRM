package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.dto.ticket.output.TicketDetailDto;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/4/4.
 */
public class JournalTicketBo {
    private RevenueOrExpense revenueOrExpense;
    private TicketDetailDto ticket;
    private Date plannedDate;
    private BigDecimal plannedMoney;

    public RevenueOrExpense getRevenueOrExpense() {
        return revenueOrExpense;
    }

    public void setRevenueOrExpense(RevenueOrExpense revenueOrExpense) {
        this.revenueOrExpense = revenueOrExpense;
    }

    public TicketDetailDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDetailDto ticket) {
        this.ticket = ticket;
    }

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
}
