package com.zifangdt.ch.base.dto.ticket.input

import org.hibernate.validator.constraints.NotEmpty

class MultiDeleteDto {
    @NotEmpty
    var ids: Set<Long>? = null
}