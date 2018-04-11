package com.zifangdt.ch.ticket.service;

import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.ticket.IP;
import com.zifangdt.ch.base.enums.ticket.TicketActionType;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.dto.ticket.TicketActionLog;
import com.zifangdt.ch.ticket.mapper.TicketActionLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TicketActionLogServer extends BaseService<TicketActionLog, Long> {

    @Autowired
    TicketActionLogMapper ticketActionLogMapper;

    @Autowired
    UaaServerApi uaaServerApi;

    @Autowired
    CommonServerApi commonServerApi;

    @Autowired
    HttpServletRequest httpServletRequest;

    public TicketActionLog create(TicketActionType actionType, Long ticketId, Long userId, Long assignedId) {
        TicketActionLog log = new TicketActionLog();
        log.setActionType(actionType);
        log.setTicketId(ticketId);
        log.setUserId(userId);
        log.setUserName(uaaServerApi.getUser(userId).getName());
        if (actionType == TicketActionType.ASSIGN){
            log.setAssignedId(assignedId);
            log.setAssignedName(uaaServerApi.getUser(assignedId).getName());
        }
        log.setIp(new IP(httpServletRequest.getRemoteAddr()));
        save(log);
        return log;
    }

    public List<TicketActionLog> getTicketLogs(Long ticketId) {
        return ticketActionLogMapper.getByTicketId(ticketId);
    }

}
