package com.zifangdt.ch.ticket.web;

import com.zifangdt.ch.base.api.TicketServerApi;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.PageDto;
import com.zifangdt.ch.base.dto.ticket.Ticket;
import com.zifangdt.ch.base.dto.ticket.input.MultiDeleteDto;
import com.zifangdt.ch.base.dto.ticket.input.TicketCreateDto;
import com.zifangdt.ch.base.dto.ticket.output.*;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.ticket.bo.AppTicketQueryBo;
import com.zifangdt.ch.ticket.bo.ReceiptTicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketSimpleQueryBo;
import com.zifangdt.ch.ticket.service.TicketService;
import eu.bitwalker.useragentutils.Application;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UaaServerApi uaaServerApi;

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping
    PageDto<Ticket> getAllTicket(TicketSimpleQueryBo bo) {
        return new PageDto<>(ticketService.getAllTicket(bo));
    }
    private static final Log LOGGER = LogFactory.getLog(TicketController.class);

    @PostMapping(value = "", params = "for=app")
    PageDto<Ticket> appQueryForTicket(@RequestBody AppTicketQueryBo bo) {
        if (bo.getView() != null) {
            if (bo.getView() == AppTicketQueryBo.ViewType.CREATE) {
                bo.setCreateId(CurrentUser.getUserId());
            } else {
                bo.setOperatorId(CurrentUser.getUserId());
            }
        }
        return new PageDto<>(ticketService.appQueryForTickets(bo));
    }

    @Deprecated
    @GetMapping("/returnVisit")
    PageDto<Ticket> getReturnVisitTicket() {
        return null;
    }

    @Deprecated
    @GetMapping("/receiptTicket")
    PageDto<Ticket> getReceiptTicket(ReceiptTicketQueryBo bo) {
        return new PageDto<>(ticketService.getReceiptTickets(bo));
    }
    /**
     * 创建工单
     * @return
     */
    @PostMapping("")
    Long createTicket(@RequestBody @Valid TicketCreateDto dto) {
        return ticketService.createTicket(dto);
    }

    @PutMapping("/{id}")
    Long updateTicket(@PathVariable("id") Long ticketId, @RequestBody @Valid TicketCreateDto dto) {
        return ticketService.updateTicket(ticketId, dto);
    }

    /**
     * 工单详情
     * @param ticketId
     * @return
     */
    @GetMapping("/{id}")
    TicketDetailDto getDetail(@PathVariable("id") Long ticketId){
        return ticketService.getTicketDetail(ticketId);
    }

    /**
     * 获取简单工单信息
     * @param ticketId
     * @return
     */
    @GetMapping("/{id}/brief")
    SimpleTicketDto getSimpleTicket(@PathVariable("id") Long ticketId) {
        TicketDetailDto detailDto = ticketService.getTicketDetail(ticketId);
        LOGGER.info("TicketDetailDto:::::::::pre"+ JSONObject.fromObject(detailDto).toString());
        if(StringUtils.isEmpty(detailDto.getTaskUrgency())){
            detailDto.setTaskUrgency("正常");
        }
        if(StringUtils.isEmpty(detailDto.getTaskUrgencyName())){
            detailDto.setTaskUrgencyName("正常");
        }

        LOGGER.info("TicketDetailDto:::::::::mid"+ JSONObject.fromObject(detailDto).toString());
        SimpleTicketDto dto = new SimpleTicketDto(detailDto);
        if (dto.getOperatorId() != null) {
            dto.setOperatorName(uaaServerApi.getUser(dto.getOperatorId()).getName());
        }
        LOGGER.info("TicketDetailDto:::::::::last"+ JSONObject.fromObject(detailDto).toString());
        return dto;
    }

    /**
     * 删除工单
     * @param ticketId
     * @return
     */
    @DeleteMapping("/{id}")
    void deleteTicket(@PathVariable("id") Long ticketId){
        ticketService.delete(ticketId);
    }

    @DeleteMapping("/batchDelete")
    void deleteTickets(@RequestBody @Valid MultiDeleteDto dto) {
        ticketService.batchDelete(dto.getIds());
    }

    @GetMapping(value = "/project/{id}", params = "for=internal")
    List<ProcedureTicketDto> getTicketForProject(@PathVariable("id") Long projectId) {
        return ticketService.getTicketsForProject(projectId);
    }

    @GetMapping(value = "/multi")
    List<TicketBrief> getMultiTicketForProject(@RequestParam("ids") Set<Long> ids) {
        return ticketService.getMulti(ids);
    }

    @GetMapping("/user/byOrgan")
    List<OrganUserDto> findAllByOrgan(){
        return ticketService.findUserByOrgan();
    }

    @GetMapping(value = {"/filter", "/filter/receipt", "/filter/returnVisit", "/filter/clearing"})
    PageDto<Ticket> getTicketByFilter(TicketQueryBo bo) {
        return new PageDto<>(ticketService.findBy(bo));
    }

    @Autowired
    TicketServerApi ticketServerApi;

    @GetMapping("/aaa")
    TicketDetailDto aaa() {
        return ticketServerApi.getDetail(99l);
    }
}
