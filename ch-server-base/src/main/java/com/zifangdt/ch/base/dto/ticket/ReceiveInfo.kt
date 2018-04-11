package com.zifangdt.ch.base.dto.ticket

import org.hibernate.validator.constraints.NotEmpty
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.NotNull
import kotlin.collections.ArrayList

/**
 * 工单应收项
 */
class ReceiveInfo {
    @NotEmpty
    var items: List<ReceiveItem>? = null // 应收项
    @NotNull
    var paymentDate: Date? = null // 回款日期
    var needBill: Boolean = false // 需要开票

    fun getTotal(): BigDecimal = items?.mapNotNull { it -> it.amount }?.fold(BigDecimal.ZERO) { acc, bigDecimal -> acc.add(bigDecimal) }
            ?: BigDecimal.ZERO
}

/**
 * 应收项
 */
class ReceiveItem {
    @NotNull
    var name: String? = null
    @NotNull
    var amount: BigDecimal? = null
}
