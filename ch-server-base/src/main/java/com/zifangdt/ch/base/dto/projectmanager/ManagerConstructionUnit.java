package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.enums.pair.TaskStatus;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;

import java.util.Date;

public class ManagerConstructionUnit {
    private Long id;

    private String constructionUnitName;

    private RefundNoticeStep messageStage;

    private Long constructionId;

    private Integer status;
    private Long unitId;
    private Date startTime;

    private String statusName;
    private String unitOrderNum;

    private TaskStatus.ProjectAccountStatus accountStatus;

    public String getUnitOrderNum() {
        return unitOrderNum;
    }

    public void setUnitOrderNum(String unitOrderNum) {
        this.unitOrderNum = unitOrderNum;
    }

    public TaskStatus.ProjectAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(TaskStatus.ProjectAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConstructionUnitName() {
        return constructionUnitName;
    }

    public void setConstructionUnitName(String constructionUnitName) {
        this.constructionUnitName = constructionUnitName == null ? null : constructionUnitName.trim();
    }

    public RefundNoticeStep getMessageStage() {
        return messageStage;
    }

    public void setMessageStage(RefundNoticeStep messageStage) {
        this.messageStage = messageStage;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }
}