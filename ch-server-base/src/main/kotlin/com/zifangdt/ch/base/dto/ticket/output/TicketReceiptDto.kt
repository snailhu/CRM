package com.zifangdt.ch.base.dto.ticket.output

import com.zifangdt.ch.base.converter.JsonPropertyTarget
import com.zifangdt.ch.base.converter.NameGenerator
import com.zifangdt.ch.base.converter.VerboseProperty
import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.ticket.Receipt
import com.zifangdt.ch.base.enums.ticket.TicketReceiptStatus
import com.zifangdt.ch.base.enums.ticket.WorkloadType
import java.math.BigDecimal
import java.util.*

class TicketReceiptDto(receipt: Receipt) : AbstractFromEntity<Receipt>(receipt) {
    var ticketId: Long? = null

    var createTime: Date? = null

    @NameGenerator
    @VerboseProperty(target = JsonPropertyTarget.USER)
    var operatorId: Long? = null

    var attachments: Array<String>? = null

    var receiptInfo: String? = null

    var workload: BigDecimal? = null

    var workloadType: WorkloadType? = null

    var receiptStatus: TicketReceiptStatus? = null

    var replyInfo: String? = null

    var attachmentDtos: List<FileIdAndName>? = null
}