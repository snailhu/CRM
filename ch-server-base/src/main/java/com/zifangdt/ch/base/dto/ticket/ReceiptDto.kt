package com.zifangdt.ch.base.dto.ticket

import com.zifangdt.ch.base.converter.JsonPropertyTarget
import com.zifangdt.ch.base.converter.NamedProperty
import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.enums.ticket.TicketReceiptStatus
import com.zifangdt.ch.base.enums.ticket.WorkloadType
import java.math.BigDecimal
import java.util.*

class ReceiptDto : AbstractFromEntity<Receipt> {
    constructor(receipt: Receipt) : super(receipt) {
        createTime = receipt.createTime
        modifyTime = receipt.modifyTime
        id = receipt.id
    }
    constructor()

    var id: Long? = null
    var createTime: Date? = null
    var modifyTime: Date? = null
    var attachments: Array<String>? = null

    var receiptInfo: String? = null

    var workload: BigDecimal? = null

    var workloadType: WorkloadType? = null

    var receiptStatus: TicketReceiptStatus? = null

    var replyInfo: String? = null

    @NamedProperty(target = JsonPropertyTarget.USER)
    var operatorId: Long? = null

    @NamedProperty(target = JsonPropertyTarget.USER)
    var replyUserId: Long? = null
}