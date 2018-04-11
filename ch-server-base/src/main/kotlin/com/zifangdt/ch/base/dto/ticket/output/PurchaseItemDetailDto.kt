package com.zifangdt.ch.base.dto.ticket.output

import com.zifangdt.ch.base.dto.product.entity.PurchaseItem
import org.springframework.beans.BeanUtils
import java.math.BigDecimal

class PurchaseItemDetailDto(purchaseItem: PurchaseItem) {

    var productId: Long? = null

    var count: Int? = null

    var price: BigDecimal? = null

    var number: String? = null

    var productName: String? = null

    var remainCount: Int? = null

    init {
        BeanUtils.copyProperties(purchaseItem, this)
        number = purchaseItem.product.number
        productName = purchaseItem.product.name
        price = purchaseItem.price
    }

}
