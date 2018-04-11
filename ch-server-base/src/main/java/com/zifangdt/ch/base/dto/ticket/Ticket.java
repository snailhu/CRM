package com.zifangdt.ch.base.dto.ticket;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NameGenerator;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.TaskUrgency;
import com.zifangdt.ch.base.enums.ticket.MaintainType;
import com.zifangdt.ch.base.enums.ticket.TicketStatus;
import com.zifangdt.ch.base.enums.ticket.TicketTypeSource;

import javax.persistence.Table;
import java.util.Date;

/**
 * 工单
 */
@Table(name = "ticket")
public class Ticket extends AuditEntity<Long>{
    private MaintainType maintainType;

    private String serialNumber;

    @NamedProperty(target = JsonPropertyTarget.CUSTOMER)
    private Long customerId;

    private String contactName;

    private String contactPhone;

    private String contactAddress;

    private Long projectProcedureId;

    private Date assignTime;

    private Remark[] remarks;

    private Long ticketTypeId;

    public MaintainType getMaintainType() {
        return maintainType;
    }

    public Ticket setMaintainType(MaintainType maintainType) {
        this.maintainType = maintainType;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Ticket setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Ticket setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public Ticket setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Ticket setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public Ticket setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
        return this;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public Ticket setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
        return this;
    }

    public TaskUrgency getTaskUrgency() {
        return taskUrgency;
    }

    public Ticket setTaskUrgency(TaskUrgency taskUrgency) {
        this.taskUrgency = taskUrgency;
        return this;
    }

    public Long getSourceContractTypeId() {
        return sourceContractTypeId;
    }

    public Ticket setSourceContractTypeId(Long sourceContractTypeId) {
        this.sourceContractTypeId = sourceContractTypeId;
        return this;
    }

    public Long getSourceProcedureId() {
        return sourceProcedureId;
    }

    public Ticket setSourceProcedureId(Long sourceProcedureId) {
        this.sourceProcedureId = sourceProcedureId;
        return this;
    }

    public String getSourceStr() {
        return sourceStr;
    }

    public Ticket setSourceStr(String sourceStr) {
        this.sourceStr = sourceStr;
        return this;
    }

    public Long getSourceOrganizationId() {
        return sourceOrganizationId;
    }

    public Ticket setSourceOrganizationId(Long sourceOrganizationId) {
        this.sourceOrganizationId = sourceOrganizationId;
        return this;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public Ticket setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
        return this;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public Ticket setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Ticket setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Ticket setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public Long getContractId() {
        return contractId;
    }

    public Ticket setContractId(Long contractId) {
        this.contractId = contractId;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Ticket setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public ReceiveInfo getReceiveInfo() {
        return receiveInfo;
    }

    public Ticket setReceiveInfo(ReceiveInfo receiveInfo) {
        this.receiveInfo = receiveInfo;
        return this;
    }

    private String ticketType;

    private TicketStatus ticketStatus;

    @NamedProperty
    private TaskUrgency taskUrgency;

    private TicketTypeSource source;

    private Long sourceContractTypeId;
    private Long sourceProcedureId;

    private String sourceStr;

    private Long sourceOrganizationId;
    private Long sourceUserId;


    private String ticketDescription;

    private Date startTime;

    private Date deadline;

    private Date acceptTime;

    private Date operateStartTime;

    private String[] attachments;

    private Long contractId;

    private Long projectId;

    private ReceiveInfo receiveInfo;

    private Boolean isDelete;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long operatorId;

    private ReturnVisit returnVisit;

    private Clearing clearing;

    private ReceiptDto receipt;

    private Date finishTime;

    public Ticket setAttachments(String[] attachments) {
        this.attachments = attachments;
        return this;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public Ticket setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public TicketTypeSource getSource() {
        return source;
    }

    public Ticket setSource(TicketTypeSource source) {
        this.source = source;
        return this;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public Ticket setDelete(Boolean delete) {
        isDelete = delete;
        return this;
    }

    public String getTicketType() {
        return ticketType;
    }

    public Ticket setTicketType(String ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public ReturnVisit getReturnVisit() {
        return returnVisit;
    }

    public Ticket setReturnVisit(ReturnVisit returnVisit) {
        this.returnVisit = returnVisit;
        return this;
    }

    public Clearing getClearing() {
        return clearing;
    }

    public Ticket setClearing(Clearing clearing) {
        this.clearing = clearing;
        return this;
    }

    public Long getProjectProcedureId() {
        return projectProcedureId;
    }

    public Ticket setProjectProcedureId(Long projectProcedureId) {
        this.projectProcedureId = projectProcedureId;
        return this;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public Ticket setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
        return this;
    }

    public ReceiptDto getReceipt() {
        return receipt;
    }

    public Ticket setReceipt(ReceiptDto receipt) {
        this.receipt = receipt;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public Ticket setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public Remark[] getRemarks() {
        return remarks;
    }

    public Ticket setRemarks(Remark[] remarks) {
        this.remarks = remarks;
        return this;
    }

    public Long getTicketTypeId() {
        return ticketTypeId;
    }

    public Ticket setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
        return this;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public Ticket setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
        return this;
    }

    public Date getOperateStartTime() {
        return operateStartTime;
    }

    public Ticket setOperateStartTime(Date operateStartTime) {
        this.operateStartTime = operateStartTime;
        return this;
    }
}