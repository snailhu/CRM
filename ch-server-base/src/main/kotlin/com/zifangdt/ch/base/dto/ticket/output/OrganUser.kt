package com.zifangdt.ch.base.dto.ticket.output

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class OrganUserDto {
    var name: String? = null
    var id: Long? = null
    var children: List<UserDto>? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class UserDto {
    var id: Long? = null
    var name: String? = null
    var username: String? = null
    var phone: String? = null
    var status: Int? = null
    var isHead: Int? = null
    var organizationName: String? = null
    var levelName: String? = null
    var unfinishTicketCount: Int = 0
}

class UserReceiptCount {
    var userId: Long? = null
    var cnt: Int? = null
}