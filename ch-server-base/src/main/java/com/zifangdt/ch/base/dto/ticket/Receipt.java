package com.zifangdt.ch.base.dto.ticket;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.ticket.TicketReceiptStatus;
import com.zifangdt.ch.base.enums.ticket.WorkloadType;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "receipt")
public class Receipt extends AuditEntity<Long>{

    private Long ticketId;

    private Long operatorId;

    public String[] getAttachments() {
        return attachments;
    }

    public Receipt setAttachments(String[] attachments) {
        this.attachments = attachments;
        return this;
    }

    public WorkloadType getWorkloadType() {
        return workloadType;
    }

    public Receipt setWorkloadType(WorkloadType workloadType) {
        this.workloadType = workloadType;
        return this;
    }

    public TicketReceiptStatus getReceiptStatus() {
        return receiptStatus;
    }

    public Receipt setReceiptStatus(TicketReceiptStatus receiptStatus) {
        this.receiptStatus = receiptStatus;
        return this;
    }

    private String[] attachments;

    private String receiptInfo;

    private BigDecimal workload;

    private WorkloadType workloadType;

    private TicketReceiptStatus receiptStatus;

    private String replyInfo;

    private Long replyUserId; // 拒绝或者确认者id

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getReceiptInfo() {
        return receiptInfo;
    }

    public void setReceiptInfo(String receiptInfo) {
        this.receiptInfo = receiptInfo == null ? null : receiptInfo.trim();
    }

    public BigDecimal getWorkload() {
        return workload;
    }

    public void setWorkload(BigDecimal workload) {
        this.workload = workload;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo == null ? null : replyInfo.trim();
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }
}