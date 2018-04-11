package com.zifangdt.ch.ticket.bo

import com.google.common.base.CaseFormat
import com.zifangdt.ch.base.dto.BaseQueryBo
import com.zifangdt.ch.base.enums.pair.TaskUrgency
import com.zifangdt.ch.base.enums.ticket.TicketStatus
import com.zifangdt.ch.base.enums.ticket.TicketTypeSource
import java.util.*

class TicketQueryBo : BaseQueryBo() {
    var serialNumber: String? = null
    var customerName: String? = null

    var taskUrgency: TaskUrgency? = null
    var ticketTypeSource: TicketTypeSource? = null // 工单类型
    var ticketStatus: TicketStatus? = null

    var beginCreateTime: Date? = null
    var endCreateTime: Date? = null
    var beginStartTime: Date? = null // 计划时间
    var endStartTime: Date? = null
    var beginAssignTime: Date? = null // 指派时间
    var endAssignTime: Date? = null
    var beginDeadlineTime: Date? = null // 完成时间
    var endDeadlineTime: Date? = null
    var beginReceiptTime: Date? = null // 回执时间
    var endReceiptTime: Date? = null
    var beginReturnVisitTime: Date? = null // 回访时间
    var endReturnVisitTime: Date? = null
    var beginClearingTime: Date? = null // 结算时间
    var endClearingTime: Date? = null
    var beginFinishTime: Date? = null // 完结时间
    var endFinishTime: Date? = null

    var customerServiceUserId: Long? = null // 客服id
    var operatorId: Long? = null // 处理人
    var contractId: Long? = null // 合同id
    var receiptOperatorId: Long? = null // 回执人
    var returnVisitOperatorId: Long? = null // 回访人
    var clearingOperatorId: Long? = null // 结算人
    var receiptStatus: ReceiptStatus? = null // 回执状态
    var returnVisitStatus: ReturnVisitStatus? = null // 回访状态
    var clearingStatus: ClearingStatus? = null // 结算状态
    var orderBy: OrderBy = OrderBy.createTime

    enum class OrderBy {
        createTime, modifyTime;

        fun getColumnName() = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name)
    }

    enum class ReceiptStatus(display: String) {
        WAIT_CONFIRM("待确认"), // 已创建，等待客服确认
        WAIT_RECEIPT("待回执"), //  回执没有创建,或者回执被拒绝，等待新创建的回执
        REJECT("已拒绝"), // 回执被拒绝
        HAS_RECEIPT("已回执") // 已经创建回执
    }

    enum class ReturnVisitStatus(display: String) {
        WAIT_RETURN_VISIT("待回访"),
        HAS_RETURN_VISIT("已回访")
    }

    enum class ClearingStatus(display: String) {
        WAIT_CLEARING("待结算"),
        HAS_CLEARING("已结算")
    }
}