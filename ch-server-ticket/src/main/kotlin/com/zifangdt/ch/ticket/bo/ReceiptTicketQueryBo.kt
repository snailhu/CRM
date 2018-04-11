package com.zifangdt.ch.ticket.bo

import com.google.common.base.CaseFormat
import com.zifangdt.ch.base.dto.BaseQueryBo
import com.zifangdt.ch.base.enums.pair.TaskUrgency
import com.zifangdt.ch.base.enums.ticket.TicketTypeSource
import java.util.*

class ReceiptTicketQueryBo : BaseQueryBo() {
    var serialNumber: String? = null
    var customerName: String? = null
    var taskUrgency: TaskUrgency? = null
    var source: TicketTypeSource? = null
    var customerServiceUserId: Long? = null
    var beginCreateTime: Date? = null
    var endCreateTime: Date? = null
    var beginAssignTime: Date? = null
    var endAssignTime: Date? = null
    var beginReceiptTime: Date? = null
    var endReceiptTime: Date? = null
    var operatorId: Long? = null
    var contractId: Long? = null
    var orderBy: OrderBy = OrderBy.createTime

    var receiptStatus: ReceiptStatus? = null

    enum class OrderBy {
        createTime, modifyTime;

        fun getColumnName() = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name)
    }

    enum class ReceiptStatus(name: String) {
        WAIT_CONFIRM("待确认"),
        WAIT_RECEIPT("待回执"),
        REJECT("已拒绝"),
        HAS_RECEIPT("已回执")
    }

}