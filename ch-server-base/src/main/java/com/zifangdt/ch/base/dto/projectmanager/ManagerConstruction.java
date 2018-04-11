package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.pair.TaskStatus;

import java.util.Date;

public class ManagerConstruction {
    private Long id;

    private String typeName;

    private Long typeId;
    private Long projectId;

    private Integer status;
    @NamedProperty
    private TaskStatus.IsNewStatus isNew;
    private String statusName;
    private TaskStatus.ProjectAccountStatus accountStatus;

    private Date startTime;

    public TaskStatus.IsNewStatus getIsNew() {
        return isNew;
    }

    public void setIsNew(TaskStatus.IsNewStatus isNew) {
        this.isNew = isNew;
    }

    public TaskStatus.ProjectAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(TaskStatus.ProjectAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}