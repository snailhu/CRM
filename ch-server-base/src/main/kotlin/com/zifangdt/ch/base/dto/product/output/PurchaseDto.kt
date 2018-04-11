package com.zifangdt.ch.base.dto.product.output

import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.product.entity.InStockChange
import com.zifangdt.ch.base.dto.product.entity.PurchaseItem
import com.zifangdt.ch.base.enums.product.PurchaseStatus
import java.math.BigDecimal

class PurchaseInStockDto(inStockChange: InStockChange) : AbstractFromEntity<InStockChange>(inStockChange) {
    var stockNumber: String? = null
    var id: Long? = null

    init {
        id = inStockChange.id
    }
}

class PurchaseBriefDto{
    var num: String? = null
    var name: String? = null
    var totalAmount: BigDecimal? = null
    var totalNum: Int? = null
    var inNum: Int? = null
    var purchaseStatus: PurchaseStatus? = null
    var providerId: Long? = null
    var providerName: String? = null
    var products: List<PurchaseItemBriefDto> = ArrayList()
}

class PurchaseItemBriefDto {
    constructor()
    constructor(purchaseItem: PurchaseItem) {
        name = purchaseItem.product.name
        number = purchaseItem.product.number
        count = purchaseItem.count
        cost = purchaseItem.product.cost
    }
    var name: String? = null
    var number: String? = null
    var count: Int? = null
    var cost: BigDecimal? = null
}