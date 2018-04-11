package com.zifangdt.ch.base.dto.ticket.input

import com.zifangdt.ch.base.dto.ToEntity
import com.zifangdt.ch.base.dto.ticket.ReceiveInfo
import com.zifangdt.ch.base.dto.ticket.Ticket
import com.zifangdt.ch.base.enums.pair.TaskUrgency
import com.zifangdt.ch.base.enums.ticket.MaintainType
import com.zifangdt.ch.base.enums.ticket.TicketTypeSource
import org.springframework.beans.BeanUtils
import java.util.*
import javax.validation.constraints.NotNull

class TicketCreateDto : ToEntity<Ticket> {
    @NotNull
    var maintainType: MaintainType? = null
    @NotNull
    var customerId: Long? = null
    var contactName: String? = null
    var contactPhone: String? = null
    var contactAddress: String? = null
    @NotNull
    var taskUrgency: TaskUrgency? = null
    var ticketDescription: String? = null
    var startTime: Date? = null
    val deadline: Date? = null
    var attachments: Array<out String>? = null
    @NotNull
    var ticketTypeId: Long? = null
    @NotNull
    var source: TicketTypeSource? = null

    //  施工拍单 CONTRACT_TYPE
    var contractId: Long? = null
    val sourceContractTypeId: Long? = null
    val sourceProcedureId: Long? = null
    var receiveInfo: ReceiveInfo? = null
    var projectProcedureId: Long? = null // 如果从项目创建，需要带上项目生成的工序id

    // 反馈工单 APP PHONE OTHER
    var sourceStr: String? = null

    // 投诉 COMPLAIN
    var sourceOrganizationId: Long? = null
    var sourceUserId: Long? = null

    override fun convertTo(): Ticket {
        var ticket = Ticket()
        BeanUtils.copyProperties(this, ticket)
        return ticket
    }
}
