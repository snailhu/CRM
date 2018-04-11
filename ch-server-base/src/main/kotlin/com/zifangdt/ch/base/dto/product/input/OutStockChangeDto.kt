package com.zifangdt.ch.base.dto.product.input

import com.zifangdt.ch.base.dto.ToEntity
import com.zifangdt.ch.base.dto.product.entity.OutStockChange
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem
import com.zifangdt.ch.base.enums.StockChangeType
import com.zifangdt.ch.base.enums.product.OutStockStatus
import com.zifangdt.ch.base.enums.product.OutStockType
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.beans.BeanUtils
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * 项目自动出库创建
 */
data class OutStockAutoProjectCreateDto(
        @NotNull
        var projectId: Long? = null,
        @NotNull
        var procedureId: Long? = null,
        @NotNull
        var procedureName: String? = null,
        @NotNull
        var contractId: Long? = null,
        @NotEmpty
        var products: List<OutStockChangeItemDto>? = null
) : ToEntity<OutStockChange> {
    override fun convertTo(): OutStockChange {
        val outStockChange = OutStockChange()
        BeanUtils.copyProperties(this, outStockChange)
        outStockChange.stockStatus = OutStockStatus.PreOutStock
        outStockChange.outStockType = OutStockType.ProjectAuto
        return outStockChange
    }
}

/**
 * 自动项目材料出库确认
 */
data class OutStockAutoProjectConfirmDto(
        var repoId: Long? = null,
        var logisticNumber: String? = null,
        var logisticCompany: String? = null,
        var deliverTime: Date? = null,
        var logisticCost: BigDecimal? = null,
        @NotEmpty var products: List<OutStockChangeItemDto>? = null
)

/**
 * 手动项目材料出库
 */
data class OutStockProjectManualDto(
        @NotNull var repoId: Long? = null,
        @NotNull var contractId: Long? = null,
        var customerName: String? = null,
        var customerAddress: String? = null,
        var customerContact: String? = null,
        @NotNull var logisticCost: Int? = null,
        var logisticNumber: String? = null,
        var logisticCompany: String? = null,
        var deliverTime: Date? = null,
        var stockStatus: OutStockStatus? = null,
        @NotEmpty var products: List<OutStockChangeItemDto>? = null
) : ToEntity<OutStockChange> {
    override fun convertTo(): OutStockChange {
        val outStockChange = OutStockChange()
        BeanUtils.copyProperties(this, outStockChange)
        outStockChange.stockStatus = OutStockStatus.OutStock
        outStockChange.outStockType = OutStockType.Project
        return outStockChange
    }
}

/**
 * 出库项目产品项
 */
data class OutStockChangeItemDto(
        @NotNull
        var productId: Long? = null,
        @Min(0)
        var realCount: Int? = null,
        var stockChangeType: StockChangeType = StockChangeType.Out
) : ToEntity<StockChangeItem> {
    override fun convertTo(): StockChangeItem {
        val stockChangeItem = StockChangeItem()
        BeanUtils.copyProperties(this, stockChangeItem)
        return stockChangeItem
    }
}

/**
 * 仓库调拨出库
 */
data class OutStockRepoExchangeDto(
        @NotNull var repoId: Long? = null,
        @NotEmpty var products: List<OutStockChangeItemDto>? = null
) : ToEntity<OutStockChange> {
    override fun convertTo(): OutStockChange {
        val change = OutStockChange()
        BeanUtils.copyProperties(this, change)
        change.outStockType = OutStockType.RepoExchange
        change.stockStatus = OutStockStatus.OutStock
        change.stockTime = Date()
        return change
    }
}