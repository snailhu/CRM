package com.zifangdt.ch.base.dto.product.output

import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.product.entity.OutStockChange
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem
import com.zifangdt.ch.base.enums.product.OutStockStatus
import org.springframework.beans.BeanUtils
import java.math.BigDecimal

class OutStockBrief : AbstractFromEntity<OutStockChange>{
    constructor(outStockChange: OutStockChange): super(outStockChange) {
        id = outStockChange.id
    }
    constructor(): super()
    var id: Long? = null
    var stockStatus: OutStockStatus? = null
    var stockNumber: String? = null
    var products: List<StockItemProductDto>? = null
}

class StockItemProductDto{
    constructor(): super()
    constructor(stockChangeItem: StockChangeItem){
        BeanUtils.copyProperties(stockChangeItem, this)
    }
    var name: String? = null
    var number: String? = null
    var productUnitName: String? = null
    var realCount: Int? = null
    var productType: String? = null
    var cost: BigDecimal? = null
    var productId: Long? = null
}