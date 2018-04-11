package com.zifangdt.ch.base.dto.projectmanager;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ConstructionUnitScheme {
    private Long id;

    private String schemeName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private String schemeDes;

    private String appendUrl;
    private String repertoryStatus;
    private String repertoryNum;
    private String schemeStatusName;
    private int schemeStatus;

    private String schemeDesignUrl;

    private Long unitId;
    private Long processId;

    @NotNull
    private Long projectId;

    public String getSchemeStatusName() {
        return schemeStatusName;
    }

    public void setSchemeStatusName(String schemeStatusName) {
        this.schemeStatusName = schemeStatusName;
    }

    public int getSchemeStatus() {
        return schemeStatus;
    }

    public void setSchemeStatus(int schemeStatus) {
        this.schemeStatus = schemeStatus;
    }

    public String getRepertoryStatus() {
        return repertoryStatus;
    }

    public void setRepertoryStatus(String repertoryStatus) {
        this.repertoryStatus = repertoryStatus;
    }

    public String getRepertoryNum() {
        return repertoryNum;
    }

    public void setRepertoryNum(String repertoryNum) {
        this.repertoryNum = repertoryNum;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    private List<ConstructionProduct> productList;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<ConstructionProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ConstructionProduct> productList) {
        this.productList = productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName == null ? null : schemeName.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSchemeDes() {
        return schemeDes;
    }

    public void setSchemeDes(String schemeDes) {
        this.schemeDes = schemeDes == null ? null : schemeDes.trim();
    }

    public String getAppendUrl() {
        return appendUrl;
    }

    public void setAppendUrl(String appendUrl) {
        this.appendUrl = appendUrl == null ? null : appendUrl.trim();
    }

    public String getSchemeDesignUrl() {
        return schemeDesignUrl;
    }

    public void setSchemeDesignUrl(String schemeDesignUrl) {
        this.schemeDesignUrl = schemeDesignUrl == null ? null : schemeDesignUrl.trim();
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}