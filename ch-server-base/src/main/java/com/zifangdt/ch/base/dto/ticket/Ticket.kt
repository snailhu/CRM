package com.zifangdt.ch.base.dto.ticket

import com.zifangdt.ch.base.enums.ticket.SatisfactionLevel
import com.zifangdt.ch.base.enums.ticket.WorkloadType
import java.math.BigDecimal
import java.util.*

class ReturnVisit {
    var satisfactionLevel: SatisfactionLevel? = null
    var info: String? = null
    var userId: Long? = null
    var username: String? = null
    var time: Date = Date()
    var createTime: Date = Date()
}

class Clearing {
    var workload = BigDecimal.ZERO
    var workloadType: WorkloadType? = null
    var price = BigDecimal.ZERO
    var travelSubsidy = BigDecimal.ZERO
    var total: BigDecimal? = null
    var remark: String? = null
    var time: Date = Date() // 创建时间
    var createTime: Date = Date() // 创建时间
    var userId: Long? = null
    var username: String? = null

    var actualTotal: BigDecimal? = null
    var actualTime: Date? = null
    var financeUserId: Long? = null
    var financeUsername: String? = null

    init {
        total = if (total != null) total else travelSubsidy + price * workload
    }

    fun updateTotal() {
        total = if (total != null) total else travelSubsidy + price * workload
    }
}