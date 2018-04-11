package com.zifangdt.ch.base.dto.ticket

import com.zifangdt.ch.base.bo.IdAndName
import com.zifangdt.ch.base.dto.ticket.output.FileIdAndName
import java.util.*
import javax.validation.constraints.NotNull

class Remark {
    var userId: Long? = null
    var username: String? = null
    var time: Date = Date()
    var info: String? = null
    var attachments: Array<FileIdAndName>? = null
}