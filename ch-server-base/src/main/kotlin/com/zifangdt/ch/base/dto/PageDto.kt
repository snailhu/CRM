package com.zifangdt.ch.base.dto

import com.github.pagehelper.Page

class PageDto<T>(pager: Page<T>) {
    var list: List<T> = ArrayList()
    var page: Int = 1
    var size: Int = 5
    var total: Long = 0
    var permittedToImport: Boolean = false
    var permittedToExport: Boolean = false

    init {
        total = pager.total
        page = pager.pageNum
        size = pager.pageSize
        list = pager.result
    }
}