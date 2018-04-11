package com.zifangdt.ch.base.dto.ticket.input

import com.zifangdt.ch.base.enums.ticket.TicketTypeSource
import javax.validation.constraints.NotNull

class TicketTypeDto {
    @NotNull
    var name: String? = null
    @NotNull
    var ticketTypeSource: TicketTypeSource? = null
}