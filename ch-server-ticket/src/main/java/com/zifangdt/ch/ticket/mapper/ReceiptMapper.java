package com.zifangdt.ch.ticket.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.ticket.Receipt;
import com.zifangdt.ch.base.dto.ticket.output.UserReceiptCount;
import com.zifangdt.ch.base.enums.ticket.TicketReceiptStatus;
import com.zifangdt.ch.ticket.bo.TicketQueryBo;

import java.util.List;
import java.util.Map;

public interface ReceiptMapper extends BaseMapper<Receipt>{
    Receipt getTicketCurrentReceipt(Long ticketId);

    List<Receipt> getTicketReceipts(Long ticketId);

    List<UserReceiptCount> findUnfinishCount(TicketReceiptStatus receiptStatus);
}