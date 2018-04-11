package com.zifangdt.ch.base.dto.projectmanager;

import javax.validation.constraints.NotNull;

public class ProjectResult {

    //关闭原因
    private String reason;

    @NotNull
    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }


}