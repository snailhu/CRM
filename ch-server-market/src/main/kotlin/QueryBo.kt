package com.zifangdt.ch.market.bo

import com.zifangdt.ch.base.dto.BaseQueryBo
class QueryBo {
    var multiStatus: List<OperateStatus> = listOf()

//    var multiStatusSet: Set<OperateStatus> = setOf()
//        get() = multiStatus.map { OperateStatus.valueOf(it) }.toSet()

    enum class OperateStatus {
        AA, BB
    }
}