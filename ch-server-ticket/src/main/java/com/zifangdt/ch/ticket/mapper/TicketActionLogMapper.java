package com.zifangdt.ch.ticket.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.ticket.TicketActionLog;

import java.util.List;

public interface TicketActionLogMapper extends BaseMapper<TicketActionLog>{
    List<TicketActionLog> getByTicketId(Long ticketId);
}