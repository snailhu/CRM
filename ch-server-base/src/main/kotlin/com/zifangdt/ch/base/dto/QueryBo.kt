package com.zifangdt.ch.base.dto

import com.google.common.base.CaseFormat


open class BaseQueryBo {

    var page: Int = 1

    var size: Int = 5

    var sort: Sort = Sort.desc

    enum class Sort {
        asc, desc;
    }
}