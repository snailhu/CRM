package com.zifangdt.ch.base.dto.ticket.output

import com.zifangdt.ch.base.bo.ManagerProjectBo
import com.zifangdt.ch.base.converter.JsonPropertyTarget
import com.zifangdt.ch.base.converter.NameGenerator
import com.zifangdt.ch.base.converter.NamedProperty
import com.zifangdt.ch.base.converter.VerboseProperty
import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.contract.Contract
import com.zifangdt.ch.base.dto.customer.Customer
import com.zifangdt.ch.base.dto.ticket.*
import com.zifangdt.ch.base.enums.pair.ExpenseStatus
import com.zifangdt.ch.base.enums.pair.ProcessStatus
import com.zifangdt.ch.base.enums.pair.TaskUrgency
import com.zifangdt.ch.base.enums.ticket.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class TicketDetailDto : AbstractFromEntity<Ticket> {
    constructor(ticket: Ticket) : super(ticket) {
        id = ticket.id
        actions = ticketStatus?.actions?.toList()
        taskUrgencyName = ticket.taskUrgency?.urgencyName?:"正常"
        taskUrgency = ticket.taskUrgency?.name ?:"正常"
        createId = ticket.createId
    }

    var id: Long? = null

    @NamedProperty(target = JsonPropertyTarget.USER)
    var createId: Long? = null

    var maintainType: MaintainType? = null

    var serialNumber: String? = null

    var customerId: Long? = null

    var contactName: String? = null

    var contactPhone: String? = null

    var contactAddress: String? = null

    var ticketType: String? = null

    var ticketTypeId: Long? = null
    var ticketStatus: TicketStatus? = null

//    var actions: List<TicketActionType>? = null

    var taskUrgency: String? = null
    var taskUrgencyName: String? = null
    var source: TicketTypeSource? = null

    var sourceData: String? = null

    var sourceContractTypeId: Long? = null
    var sourceProcedureId: Long? = null

    var sourceStr: String? = null

    var sourceOrganizationId: Long? = null
    var sourceUserId: Long? = null


    var ticketDescription: String? = null

    var startTime: Date? = null

    var deadline: Date? = null
    var acceptTime: Date? = null
    var operateStartTime: Date? = null
    var attachments: Array<String>? = null
    var attachmentDtos: List<FileIdAndName>? = null

    var contractId: Long? = null

    var projectId: Long? = null

    var receiveInfo: ReceiveInfo? = null

    var isDelete: Boolean? = null

    @NameGenerator
    @VerboseProperty(target = JsonPropertyTarget.USER)
    var operatorId: Long? = null

    var ticketTypeSourceName: String? = null
    var sourceContractTypeName: String? = null
    var sourceProcedureName: String? = null

    var sourceOrganizationName: String? = null
    var sourceUserName: String? = null

    var customer: Customer? = null
    var contract: ContractDto? = null
    var project: ProjectDto? = null

    var receipt: ReceiptDto? = null

    // 工单流程日志
    var logs: List<TicketActionLog>? = null

    // 工单回执
    var receipts: List<TicketReceiptDto>? = null

    var returnVisit: ReturnVisit? = null
    var clearing: Clearing? = null
    var remarks: Array<Remark>? = null

    var actions: List<TicketActionType>? = null
}

class ContractDto(contract: Contract) : AbstractFromEntity<Contract>(contract) {

    var id: Long? = null
    var name: String? = null
    var printNumber: String? = null
    @NamedProperty
    var processStatus: ProcessStatus = ProcessStatus.APPROVED
    var contractTypeNames: List<String>? = null
    var types: List<Long>? = null

    init {
        id = contract.id
        name = contract.number
        printNumber = contract.printNumber
    }
}

class ProjectDto(projectBo: ManagerProjectBo) {

    var projectName: String? = null
    var statusName: String? = null
    var projectManager: String? = null
    var construction: String? = null

    init {
        projectName = projectBo.projectName
        statusName = projectBo.statusName
        projectManager = projectBo.projectManager
        construction = projectBo.construction
    }
}

class TicketActionLogDto(ticketActionLog: TicketActionLog) : AbstractFromEntity<TicketActionLog>(ticketActionLog) {
    var userId: Long? = null

    var userName: String? = null

    var ticketId: Long? = null

    var actionType: TicketActionType? = null

    var releaseVersion: String? = null

    var platform: Platform? = null

    var ip: IP? = null

    var location: String? = null

    var assignedId: Long? = null

    var assignedName: Long? = null

    var actionTypeTemplate: String? = null

    init {
        actionTypeTemplate = actionType?.contentTemplate
    }
}

class TicketBrief(ticket: Ticket) : AbstractFromEntity<Ticket>(ticket) {

    var id: Long? = null
    // 序号
    var serialNumber: String? = null
    var maintainType: MaintainType? = null
    var operatorName: String? = null
    var description: String? = null
    var total: BigDecimal? = null
    var actualTotal: BigDecimal? = null
    @NamedProperty
    var expenseStatus: ExpenseStatus = ExpenseStatus.PENDING
    var contractTypeId: Long? = null
    var projectDto: ProjectDto? = null
    var procedureName: String? = null
    var receiveInfo: ReceiveInfo? = null
    var clearing: Clearing? = null
    var clearingRemark: String = ""
    var expenseId: Long? = null

    init {
        id = ticket.id
        contractTypeId = ticket.sourceContractTypeId
        if (ticket.clearing == null) {
            description = "还未结算"
            total = BigDecimal.ZERO
            actualTotal = BigDecimal.ZERO
        } else {
            description = "工作量：${ticket.clearing.workload} ${ticket.clearing.workloadType?.getName()} " +
                    "${ticket.clearing.price}元/${ticket.clearing.workloadType?.getName()}   差旅补贴：${ticket.clearing.travelSubsidy}元"
            total = ticket.clearing.total
            actualTotal = if (ticket.clearing.actualTotal == null) BigDecimal.ZERO else ticket.clearing.actualTotal
            if (ticket.clearing.actualTotal != null)
                expenseStatus = ExpenseStatus.PROCESSED
            clearingRemark = ticket.clearing.remark ?: ""
        }
    }
}

class SimpleTicketDto(ticketDetailDto: TicketDetailDto) {

    var receiptStatusName: String? = "未回执"
    var ticketStatus: TicketStatus? = null
    var operatorId: Long? = null // 执行人
    var operatorName: String? = null
    var ticketId: Long? = null

    init {
        ticketId = ticketDetailDto.id
        operatorId = ticketDetailDto.operatorId
        ticketStatus = ticketDetailDto.ticketStatus
        receiptStatusName = ticketDetailDto.receipt?.receiptStatus?.getName()
    }
}

data class FileIdAndName(
        val id: String,
        val name: String)
