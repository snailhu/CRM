package com.zifangdt.ch.ticket.service;

import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.dto.common.File;
import com.zifangdt.ch.base.dto.ticket.input.CreateReceipt;
import com.zifangdt.ch.base.dto.ticket.input.ConfirmRejectReceiptDto;
import com.zifangdt.ch.base.dto.ticket.output.FileIdAndName;
import com.zifangdt.ch.base.dto.ticket.output.TicketReceiptDto;
import com.zifangdt.ch.base.dto.ticket.output.UserReceiptCount;
import com.zifangdt.ch.base.enums.ticket.TicketActionType;
import com.zifangdt.ch.base.enums.ticket.TicketReceiptStatus;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.dto.ticket.Receipt;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.ticket.mapper.ReceiptMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReceiptService extends BaseService<Receipt, Long> {
    @Autowired
    ReceiptMapper receiptMapper;

    @Autowired
    CommonServerApi commonServerApi;

    public Receipt create(CreateReceipt createReceipt) {
        Receipt receipt = new Receipt();
        BeanUtils.copyProperties(createReceipt, receipt);
        receipt.setReceiptStatus(TicketReceiptStatus.CREATE);
        receipt.setOperatorId(CurrentUser.getUserId());
        receipt.setReceiptInfo(createReceipt.getInfo());
        save(receipt);
        return receipt;
    }

    public Receipt reject(ConfirmRejectReceiptDto dto) {
        Receipt receipt = getTicketCurrentReceipt(dto.getTicketId());
        receipt.setReplyInfo(dto.getInfo());
        receipt.setReceiptStatus(TicketReceiptStatus.REJECT);
        receipt.setReplyUserId(CurrentUser.getUserId());
        updateInternal(receipt);
        return receipt;
    }

    public Receipt confirm(ConfirmRejectReceiptDto dto) {
        Receipt receipt = getTicketCurrentReceipt(dto.getTicketId());
        receipt.setReplyInfo(dto.getInfo());
        receipt.setReceiptStatus(TicketReceiptStatus.CONFIRM);
        receipt.setReplyUserId(CurrentUser.getUserId());
        receipt.setAttachments(ArrayUtils.addAll(receipt.getAttachments(), dto.getAttachments()));
        updateInternal(receipt);
        return receipt;
    }

    public List<TicketReceiptDto> getTicketReceipts(Long ticketId) {
        List<Receipt> receipts = receiptMapper.getTicketReceipts(ticketId);
        List<TicketReceiptDto> result = receipts.stream()
                .map(TicketReceiptDto::new)
                .collect(Collectors.toList());
        for (TicketReceiptDto ticketReceiptDto : result) {
            if (ticketReceiptDto.getAttachments() != null && ticketReceiptDto.getAttachments().length != 0) {
                List<File> files = commonServerApi.fileInfo(Arrays.asList(ticketReceiptDto.getAttachments()));
                ticketReceiptDto.setAttachmentDtos(files.stream().map(file -> new FileIdAndName(file.getId(), file.getName())).collect(Collectors.toList()));
            }
        }
        return result;
    }

    /**
     * 获取工单当前的回执，如果没有回执返回null
     *
     * @param ticketId
     * @return
     */
    public Receipt getTicketCurrentReceipt(Long ticketId) {
        return receiptMapper.getTicketCurrentReceipt(ticketId);
    }

    public Boolean isPermit(TicketActionType actionType, Long ticketId) {
        assert Arrays.asList(
                TicketActionType.CREATE_RECEIPT,
                TicketActionType.CONFIRM_RECEIPT,
                TicketActionType.REJECT_RECEIPT).contains(actionType);
        Boolean isPermit = false;
        Receipt receipt = getTicketCurrentReceipt(ticketId);
        switch (actionType) {
            case CREATE_RECEIPT:
                if (receipt == null || receipt.getReceiptStatus() != TicketReceiptStatus.CREATE) {
                    isPermit = true;
                }
                break;
            case CONFIRM_RECEIPT:
            case REJECT_RECEIPT:
                if (receipt != null && receipt.getReceiptStatus() == TicketReceiptStatus.CREATE) {
                    isPermit = true;
                }
                break;
        }
        return isPermit;
    }

    @Deprecated
    public Map<Long,Integer> findUnfinishCount(TicketReceiptStatus receiptStatus) {
        List<UserReceiptCount> urc = receiptMapper.findUnfinishCount(receiptStatus);
        return urc.stream().collect(Collectors.toMap(UserReceiptCount::getUserId, UserReceiptCount::getCnt));
    }
}
