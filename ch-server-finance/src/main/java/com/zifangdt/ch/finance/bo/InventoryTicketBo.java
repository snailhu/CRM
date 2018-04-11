package com.zifangdt.ch.finance.bo;

import com.zifangdt.ch.base.dto.ticket.output.TicketBrief;
import com.zifangdt.ch.base.enums.ClearingStatus;
import com.zifangdt.ch.base.enums.pair.ExpenseStatus;

/**
 * Created by 袁兵 on 2018/2/7.
 */
public class InventoryTicketBo {
    private TicketBrief ticket;
    private ClearingStatus status;

    public InventoryTicketBo() {
    }

    public InventoryTicketBo(TicketBrief ticket) {
        this.ticket=ticket;
        this.status = ticket.getExpenseStatus()==ExpenseStatus.PROCESSED ? ClearingStatus.APPROVED : ClearingStatus.PENDING;
    }

    public TicketBrief getTicket() {
        return ticket;
    }

    public void setTicket(TicketBrief ticket) {
        this.ticket = ticket;
    }

    public ClearingStatus getStatus() {
        return status;
    }

    public void setStatus(ClearingStatus status) {
        this.status = status;
    }
}
