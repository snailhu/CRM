package com.zifangdt.ch.base.dto.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.ticket.Platform;
import com.zifangdt.ch.base.enums.ticket.TicketActionType;

import javax.persistence.Table;

@JsonIgnoreProperties(value = {"contentTemplate", "arguments"}, allowGetters = true)
@Table(name = "ticket_action_log")
public class TicketActionLog extends AuditEntity<Long> {

    private Long userId;

    private String userName;

    private Long ticketId;

    private TicketActionType actionType;

    private String releaseVersion;

    private Platform platform;

    private IP ip;

    private String location;

    private Long assignedId;

    private String assignedName;

    private long transferId;

    private String transferName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion == null ? null : releaseVersion.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public IP getIp() {
        return ip;
    }

    public TicketActionLog setIp(IP ip) {
        this.ip = ip;
        return this;
    }

    public TicketActionType getActionType() {
        return actionType;
    }

    public TicketActionLog setActionType(TicketActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public Platform getPlatform() {
        return platform;
    }

    public TicketActionLog setPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public Long getAssignedId() {
        return assignedId;
    }

    public TicketActionLog setAssignedId(Long assignedId) {
        this.assignedId = assignedId;
        return this;
    }

    public String getAssignedName() {
        return assignedName;
    }

    public TicketActionLog setAssignedName(String assignedName) {
        this.assignedName = assignedName;
        return this;
    }

    public String getContentTemplate() {
        return actionType.getContentTemplate();
    }

    public String[] getArguments() {
        if (actionType == TicketActionType.ASSIGN) {
            return new String[]{userName, assignedName};
        } else {
            return new String[] {userName};
        }
    }

    public long getTransferId() {
        return transferId;
    }

    public TicketActionLog setTransferId(long transferId) {
        this.transferId = transferId;
        return this;
    }

    public String getTransferName() {
        return transferName;
    }

    public TicketActionLog setTransferName(String transferName) {
        this.transferName = transferName;
        return this;
    }
}