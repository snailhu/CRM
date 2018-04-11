package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.dto.ticket.input.ClearingConfirmDto;
import com.zifangdt.ch.base.dto.ticket.output.ProcedureTicketDto;
import com.zifangdt.ch.base.dto.ticket.output.SimpleTicketDto;
import com.zifangdt.ch.base.dto.ticket.output.TicketBrief;
import com.zifangdt.ch.base.dto.ticket.output.TicketDetailDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@FeignClient("ticket-server")
public interface TicketServerApi {

    @GetMapping("/ticket/{id}")
    TicketDetailDto getDetail(@PathVariable("id") Long ticketId);

    /**
     * 获取客服人员id集合
     * @return
     */
    @GetMapping("/configs/customerServiceStaff")
    Set<Long> getCustomerServiceStaff();

    /**
     * 财务确认工单结算
     * @param dto
     */
    @PutMapping(value = "/ticket/confirmClearing?for=internal")
    void confirmClearing(@RequestBody @Valid ClearingConfirmDto dto);

    /**
     * 获取项目工单结算
     * @param id
     * @return
     */
    @GetMapping(value = "/ticket/project/{id}?for=internal")
    List<ProcedureTicketDto> getTicketForProject(@PathVariable("id") Long id);

    /**
     * 获取多个工单
     * @param ids
     * @return
     */
    @GetMapping(value = "/ticket/multi")
    List<TicketBrief> getMultiTicketForProject(@RequestParam("ids") Set<Long> ids);

    /**
     * 获取简单工单信息
     * @param ticketId
     * @return
     */
    @GetMapping("/ticket/{id}/brief")
    SimpleTicketDto getSimpleTicket(@PathVariable("id") Long ticketId);

    @GetMapping("/configs/ticketTypes")
    List<IdAndName> ticketTypes();
}
