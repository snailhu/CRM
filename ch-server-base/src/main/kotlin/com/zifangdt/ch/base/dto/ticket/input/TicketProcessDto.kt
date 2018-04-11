package com.zifangdt.ch.base.dto.ticket.input

import com.zifangdt.ch.base.bo.IdAndName
import com.zifangdt.ch.base.enums.ticket.SatisfactionLevel
import com.zifangdt.ch.base.enums.ticket.WorkloadType
import com.zifangdt.ch.base.validation.TicketExist
import com.zifangdt.ch.base.validation.UserExist
import org.hibernate.validator.constraints.NotEmpty
import java.math.BigDecimal
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull

/**
 * 工单流程
 */

open class BaseTicketProcessDto {
    @NotNull
    @TicketExist
    var ticketId: Long? = null
}

class TicketAssignDto : BaseTicketProcessDto() {
    @NotNull
    @UserExist
    var userId: Long? = null
}


class TicketAssignRejectDto : BaseTicketProcessDto() {
    var info: String? = null
}

class TicketRemarkDto: BaseTicketProcessDto() {
    @NotNull
    var info: String? = null
    var attachments: List<String>? = ArrayList()
}

class CreateReceipt: BaseTicketProcessDto() {
    @NotEmpty
    var info: String? = null
    var attachments: Array<out String>? = null
    @NotNull
    @Max(999.99.toLong(), message = "不能超过999")
    var workload: BigDecimal? = null
    @NotNull
    var workloadType: WorkloadType? = null
}

class ConfirmRejectReceiptDto : BaseTicketProcessDto() {
    var info: String? = null
    var attachments: Array<String>? = null
}

class ReturnVisitDto: BaseTicketProcessDto() {
    @NotNull
    var satisfactionLevel: SatisfactionLevel? = null
    var info: String? = null
}

class ClearingDto: BaseTicketProcessDto() {
    @NotNull
    var workload: BigDecimal? = null
    @NotNull
    var price: BigDecimal? = null
    var travelSubsidy: BigDecimal = BigDecimal.ZERO
    var remark: String? = null
    var total: BigDecimal? = null
}

class ClearingConfirmDto: BaseTicketProcessDto() {
    // 实际结算金额
    @NotNull
    var actualTotal: BigDecimal? = null
    // 结算人id
    @NotNull
    var financeUserId: Long? = null
    // 结算人姓名
    @NotNull
    var financeUsername: String? = null
}