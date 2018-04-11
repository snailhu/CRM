package com.zifangdt.ch.base.bo.cfg.base;

import com.zifangdt.ch.base.enums.ticket.TicketTypeSource;

public class DetailOfTicketType extends BaseConfigDetail{
    private String name;
    private TicketTypeSource ticketTypeSource;
    private Boolean deletable = true;

    public String getName() {
        return name;
    }

    public DetailOfTicketType setName(String name) {
        this.name = name;
        return this;
    }

    public TicketTypeSource getTicketTypeSource() {
        return ticketTypeSource;
    }

    public DetailOfTicketType setTicketTypeSource(TicketTypeSource ticketTypeSource) {
        this.ticketTypeSource = ticketTypeSource;
        return this;
    }

    public Boolean getDeletable() {
        return deletable;
    }

    public DetailOfTicketType setDeletable(Boolean deletable) {
        this.deletable = deletable;
        return this;
    }
}
