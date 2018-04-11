package com.zifangdt.ch.ticket.bo

import com.google.common.base.CaseFormat
import com.zifangdt.ch.base.dto.BaseQueryBo
import com.zifangdt.ch.base.enums.ticket.DisplayableEnum
import com.zifangdt.ch.base.enums.ticket.TicketStatus
import java.util.*

class TicketSimpleQueryBo : BaseQueryBo() {
    var ticketStatus: TicketStatus? = null
    var search: String? = null

    var userIds: Set<Long> = HashSet()
}

class AppTicketQueryBo : BaseQueryBo() {
    var view: ViewType? = null
    var singleStatus: SingleStatus? = null
    var multiStatus: List<OperateStatus> = listOf()
    var beginDeadline: Date? = null
    var endDeadline: Date? = null
    var beginFinishTime: Date? = null
    var endFinishTime: Date? = null
    var createId: Long? = null
    var operatorId: Long? = null
    var orderBy: OrderBy = OrderBy.createTime
    var search: String? = null

    enum class ViewType(private val displayName: String): DisplayableEnum{
        CREATE("我创建的"),
        CHARGE("我负责的");
        override fun getName(): String = this.displayName
    }

    /**
     * 状态 单选
     */
    enum class SingleStatus(private val ticketStatus: TicketStatus?, private val displayName: String): DisplayableEnum {
        NOT_FINISH(null, "未完成"),
        WAIT_ASSIGN(TicketStatus.WAIT_ASSIGN, "待指派"),
        ASSIGNED(TicketStatus.ASSIGNED, "已指派"),
        ACCEPTED(TicketStatus.ACCEPTED, "已接受"),
        ON_GOING(TicketStatus.ON_GOING, "进行中"),
        WAIT_FINISH(TicketStatus.WAIT_FINISH, "待完结"),
        FINISH(TicketStatus.FINISH, "已完结");

        override fun getName(): String = this.displayName
        fun getStatus(): TicketStatus? = this.ticketStatus
    }

    /**
     * 更多状态 多选
     */
    enum class OperateStatus(private var display: String): DisplayableEnum {
        WAIT_RECEIPT("待回执"),
        HAS_RECEIPT("已回执"),
        REJECT("已拒绝"),
        HAS_CLEARING("已结算"),
        WAIT_RETURN_VISIT("待回访"),
        HAS_RETURN_VISIT("已回访"),
        WAIT_CLEARING("待结算"),
        HAS_CLEARING_CHECK("已到账");
        override fun getName(): String = this.display
    }

    enum class OrderBy {
        createTime, modifyTime;

        fun getColumnName() = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name)
    }

    var wait_receipt = false
        get() = OperateStatus.WAIT_RECEIPT in multiStatus
    var has_receipt = false
        get() = OperateStatus.HAS_RECEIPT in multiStatus
    var reject = false
        get() = OperateStatus.REJECT in multiStatus
    var has_clearing = false
        get() = OperateStatus.HAS_CLEARING in multiStatus
    var wait_return_visit = false
        get() = OperateStatus.WAIT_RETURN_VISIT in multiStatus
    var has_return_visit = false
        get() = OperateStatus.HAS_RETURN_VISIT in multiStatus
    var wait_clearing = false
        get() = OperateStatus.WAIT_CLEARING in multiStatus
    var has_clearing_check = false
        get() = OperateStatus.HAS_CLEARING_CHECK in multiStatus


}