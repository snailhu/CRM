package com.zifangdt.ch.ticket.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.ticket.Ticket;
import com.zifangdt.ch.base.dto.ticket.output.UserReceiptCount;
import com.zifangdt.ch.ticket.bo.AppTicketQueryBo;
import com.zifangdt.ch.ticket.bo.ReceiptTicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketSimpleQueryBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TicketMapper extends BaseMapper<Ticket>{
    Long countFor(String format);

    List<Ticket> findTicketForProject(Long projectId);

    List<Ticket> findBy(TicketSimpleQueryBo bo);

    /**
     * 复杂查询
     * @param bo
     * @return
     */
    List<Ticket> findAdvanceBy(TicketQueryBo bo);

    List<Ticket> findReceiptTickets(ReceiptTicketQueryBo bo);

    List<Ticket> findForApp(AppTicketQueryBo bo);

    List<Ticket> getMulti(@Param("ids") Set<Long> ids);

    List<UserReceiptCount> findUnfinishCount();
}