package com.zifangdt.ch.base.dto.ticket.output

import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.ticket.Ticket
import com.zifangdt.ch.base.enums.ticket.WorkloadType
import java.math.BigDecimal

class ProcedureTicketDto : AbstractFromEntity<Ticket> {
    constructor() : super()
    constructor(ticket: Ticket) : super(ticket) {
        if (ticket.clearing == null) {
            description = "还未结算"
            total = BigDecimal.ZERO
            actualTotal = BigDecimal.ZERO
        } else {
            description = "工作量：${ticket.clearing.workload} ${ticket.clearing.workloadType?.getName()} " +
                    "${ticket.clearing.price}元/${ticket.clearing.workloadType?.getName()}   差旅补贴：${ticket.clearing.travelSubsidy}元"
            total = ticket.clearing.total
            actualTotal = if (ticket.clearing.actualTotal == null) BigDecimal.ZERO else ticket.clearing.actualTotal
        }
    }

    var username: String? = null
    var serialNumber: String? = null
    var procedureName: String? = null
    var total: BigDecimal? = null
    var actualTotal: BigDecimal? = null
    var workDescription: WorkDescription? = null
    var description: String? = null
}

class WorkDescription {
    var workload: Float? = null
    var price: Float? = null
    var workloadType: WorkloadType? = null
    var travelSubsidy: Float? = null
}