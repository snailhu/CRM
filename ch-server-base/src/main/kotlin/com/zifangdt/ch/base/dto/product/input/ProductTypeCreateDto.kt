package com.zifangdt.ch.base.dto.product.input

import com.zifangdt.ch.base.dto.ToEntity
import com.zifangdt.ch.base.dto.product.entity.ProductType
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.beans.BeanUtils

data class ProductTypeCreateDto(
        @NotEmpty val name: String? = null,
        var description: String? = null
) : ToEntity<ProductType> {
    override fun convertTo(): ProductType {
        val productType = ProductType()
        BeanUtils.copyProperties(this, productType)
        return productType
    }
}
