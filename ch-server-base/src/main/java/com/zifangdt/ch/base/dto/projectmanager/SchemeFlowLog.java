package com.zifangdt.ch.base.dto.projectmanager;

import java.util.Date;

public class SchemeFlowLog {
    private Long id;

    private Long schemeId;

    private Long flowId;

    private String description;

    private String executeReason;

    private String executor;

    private Long executorId;

    private Date createTime;

    private String flowName;

    private Integer flowStatus;

    private Long parentId;

    private String ableExecutor;

    private String ableExecutorId;

    private String statusName;

    private Integer flowSort;

    public Integer getFlowSort() {
        return flowSort;
    }

    public void setFlowSort(Integer flowSort) {
        this.flowSort = flowSort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAbleExecutor() {
        return ableExecutor;
    }

    public void setAbleExecutor(String ableExecutor) {
        this.ableExecutor = ableExecutor == null ? null : ableExecutor.trim();
    }

    public String getAbleExecutorId() {
        return ableExecutorId;
    }

    public void setAbleExecutorId(String ableExecutorId) {
        this.ableExecutorId = ableExecutorId == null ? null : ableExecutorId.trim();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }
}