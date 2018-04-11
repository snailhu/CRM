package com.zifangdt.ch.base.dto.product.output

import com.zifangdt.ch.base.dto.AbstractFromEntity
import com.zifangdt.ch.base.dto.product.entity.ProductType

class ProductTypeBrief(
        source: ProductType
) : AbstractFromEntity<ProductType>(source) {
    var name: String? = null
    var description: String? = null
    var sort: Int? = null
    var id: Long? = null
    var count: Int? = null

    init {
        id = source.id
    }
}
