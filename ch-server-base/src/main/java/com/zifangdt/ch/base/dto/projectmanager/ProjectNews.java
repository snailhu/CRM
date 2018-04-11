package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.dto.BaseNews;
import com.zifangdt.ch.base.enums.CustomerNewsType;

public class   ProjectNews extends BaseNews {

    private CustomerNewsType type;

    private Long customerId;
    private Long projectId;
    private String projectName;
    private String operatorName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public CustomerNewsType getType() {
        return type;
    }

    public void setType(CustomerNewsType type) {
        this.type = type;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}