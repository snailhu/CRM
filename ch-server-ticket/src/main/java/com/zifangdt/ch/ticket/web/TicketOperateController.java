package com.zifangdt.ch.ticket.web;

import com.zifangdt.ch.base.dto.ticket.Remark;
import com.zifangdt.ch.base.dto.ticket.input.*;
import com.zifangdt.ch.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ticket")
public class TicketOperateController {
     @Autowired
     TicketService ticketService;

     /**
     * 指派工单
     */
    @PutMapping("/assign")
    void assignTicket(@RequestBody @Valid TicketAssignDto dto) {
        ticketService.assign(dto);
    }

    /**
     * 接受工单
     * @param dto
     */
    @PutMapping("/accept")
    void acceptTicket(@RequestBody @Valid BaseTicketProcessDto dto) {
        ticketService.accept(dto);
    }

    /**
     * 拒绝指派
     * @param dto
     */
    @PutMapping("/reject")
    void rejectAssign(@RequestBody @Valid TicketAssignRejectDto dto) {
        ticketService.reject(dto);
    }

    /**
     * 开始工单
     */
    @PutMapping("/start")
    void startTicket(@RequestBody @Valid BaseTicketProcessDto dto) {
        ticketService.startTicket(dto);
    }

    /**
     * 完成任务，生成回执
     * @param dto
     */
    @PutMapping("/createReceipt")
    void createReceipt(@RequestBody @Valid CreateReceipt dto) {
        ticketService.createReceipt(dto);
    }

    /**
     * 拒绝回执
     * @param dto
     */
    @PutMapping("/rejectReceipt")
    void rejectReceipt(@RequestBody @Valid ConfirmRejectReceiptDto dto) {
        ticketService.rejectReceipt(dto);
    }

    /**
     * 确认回执
     * @param dto
     */
    @PutMapping("/confirmReceipt")
    void confirmReceipt(@RequestBody @Valid ConfirmRejectReceiptDto dto) {
        ticketService.confirmReceipt(dto);
    }

    /**
     * 创建回访
     */
    @PutMapping("/createReturnVisit")
    void createReturnVisit(@RequestBody @Valid ReturnVisitDto dto) {
        ticketService.createReturnVisit(dto);
    }

    /**
     * 创建结算
     */
    @PutMapping("/createClearing")
    void createClearing(@RequestBody @Valid ClearingDto dto) {
        ticketService.createClearing(dto);
    }

    /**
     * 工单完结
     * @param dto
     */
    @PutMapping("/finish")
    void finishTicket(@RequestBody @Valid BaseTicketProcessDto dto) {
        ticketService.finishTicket(dto);
    }

    /**
     * 工单清算确认，内部接口
     * @param dto
     */
    @PutMapping(value = "/confirmClearing", params = "for=internal")
    void confirmClearing(@RequestBody @Valid ClearingConfirmDto dto) {
        ticketService.confirmClearing(dto);
    }

    @PutMapping(value = "/remark")
    void addRemark(@RequestBody @Valid TicketRemarkDto dto) {
        ticketService.addRemark(dto);
    }
}
