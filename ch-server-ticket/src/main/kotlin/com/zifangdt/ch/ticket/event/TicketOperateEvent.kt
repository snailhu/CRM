package com.zifangdt.ch.ticket

import com.zifangdt.ch.base.dto.ticket.Ticket
import com.zifangdt.ch.base.enums.ticket.TicketActionType
import org.springframework.context.ApplicationEvent



class TicketOperateEvent(source: Any, val ticket: Ticket, val actionType: TicketActionType) : ApplicationEvent(source)
