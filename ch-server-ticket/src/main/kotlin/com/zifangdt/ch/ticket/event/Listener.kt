package com.zifangdt.ch.ticket.event

import com.zifangdt.ch.base.api.CommonServerApi
import com.zifangdt.ch.base.api.TodoServerApi
import com.zifangdt.ch.base.constant.NoticeTags
import com.zifangdt.ch.base.dto.common.Notice
import com.zifangdt.ch.base.dto.todo.TodoTask
import com.zifangdt.ch.base.enums.NoticeType
import com.zifangdt.ch.base.enums.RedirectType
import com.zifangdt.ch.base.enums.pair.TaskType
import com.zifangdt.ch.base.enums.ticket.TicketActionType.*
import com.zifangdt.ch.base.util.CurrentUser
import com.zifangdt.ch.ticket.TicketOperateEvent
import com.zifangdt.ch.ticket.service.ConfigItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class Listener {

    val NAME = "工单"

    @Autowired
    lateinit var commonServerApi: CommonServerApi

    @Autowired
    lateinit var configItemService: ConfigItemService

    @Autowired
    lateinit var todoServerApi: TodoServerApi

    @EventListener
    fun onTicketOperate(ticketOperateEvent: TicketOperateEvent) {
        val currentUserId = checkNotNull(CurrentUser.getUserId())
        val ticket = ticketOperateEvent.ticket
        try { todoServerApi.operateTicketTask(ticket.id) } catch (e: Exception) { }

        fun makeNotice(noticeType: NoticeType, receivers: List<Long>) {
            if (receivers.isEmpty()) return
            val builder = Notice.newBuilder().name(NAME).tag(NoticeTags.TICKET).redirect(RedirectType.Ticket, ticket.id)
            todoServerApi.autoCreateTask(TodoTask.newBuilder()
                    .name("工单："+ticket.serialNumber+",待执行")
                    .receivers(receivers)
                    .type(TaskType.WORK_ORDER)
                    .urgency(ticket.taskUrgency)
                    .redirect(RedirectType.Ticket, ticket.id).build()
                    )
            commonServerApi.saveNotice(builder
                    .type(noticeType, CurrentUser.getName(), ticket.serialNumber)
                    .receivers(receivers)
                    .build())
        }

        val actionType = ticketOperateEvent.actionType
        when (actionType) {
            CREATE -> {
                val ids = configItemService.customerServiceStaff()
                makeNotice(NoticeType.TICKET_CREATE, ids.toList())
            }
            ASSIGN -> makeNotice(NoticeType.TICKET_ASSIGN, listOf(ticket.operatorId))
            EDIT -> {
            }
            DELETE -> if (ticket.operatorId != null) makeNotice(NoticeType.TICKET_DELETE, listOf(ticket.operatorId))
            ACCEPT -> makeNotice(NoticeType.TICKET_ACCEPT, listOf(ticket.createId))
            REJECT -> makeNotice(NoticeType.TICKET_REJECT, listOf(ticket.createId))
            TRANSFER -> TODO()
            START -> makeNotice(NoticeType.TICKET_START, listOf(ticket.createId))
            CREATE_RECEIPT -> makeNotice(NoticeType.TICKET_CREATE_RECEIPT, listOf(ticket.createId))
            REJECT_RECEIPT -> makeNotice(NoticeType.TICKET_REJECT_RECEIPT, listOf(ticket.operatorId))
            CONFIRM_RECEIPT -> makeNotice(NoticeType.TICKET_CONFIRM_RECEIPT, listOf(ticket.operatorId))
            CREATE_RETURN_VISIT -> makeNotice(NoticeType.TICKET_RETURN_VISIT, listOf(ticket.operatorId))
            CREATE_CLEARING -> makeNotice(NoticeType.TICKET_CREATE_CLEARING, listOf(ticket.operatorId))
            FINISH -> makeNotice(NoticeType.TICKET_FINISH, listOf(ticket.operatorId))
            REMARK -> {
                val receivers = mutableSetOf(ticket.createId, ticket.operatorId)
                receivers.remove(currentUserId)
                makeNotice(NoticeType.TICKET_NEW_REMARK, receivers.toList())
            }
            CONFIRM_CLEARING -> makeNotice(NoticeType.TICKET_CLEARING_CONFIRM, listOf(ticket.operatorId))
        }
    }
}
