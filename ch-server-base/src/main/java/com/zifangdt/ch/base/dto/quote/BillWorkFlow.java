package com.zifangdt.ch.base.dto.quote;

import java.util.Date;

public class BillWorkFlow {
    private Long id;

    private Long quoteBillId;

    private Long flowId;

    private String description;

    private String executeReason;
    private String executeReasonUrl;

    private String executor;
    private String ableExecutor;

    private Long executorId;
    private String ableExecutorId;

    private Date createTime;

    private String flowName;

    private Integer flowStatus;

    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private Long parentId;

    public String getExecuteReasonUrl() {
        return executeReasonUrl;
    }

    public void setExecuteReasonUrl(String executeReasonUrl) {
        this.executeReasonUrl = executeReasonUrl;
    }

    public String getAbleExecutor() {
        return ableExecutor;
    }

    public void setAbleExecutor(String ableExecutor) {
        this.ableExecutor = ableExecutor;
    }

    public String getAbleExecutorId() {
        return ableExecutorId;
    }

    public void setAbleExecutorId(String ableExecutorId) {
        this.ableExecutorId = ableExecutorId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteBillId() {
        return quoteBillId;
    }

    public void setQuoteBillId(Long quoteBillId) {
        this.quoteBillId = quoteBillId;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getExecuteReason() {
        return executeReason;
    }

    public void setExecuteReason(String executeReason) {
        this.executeReason = executeReason == null ? null : executeReason.trim();
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName == null ? null : flowName.trim();
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }
}