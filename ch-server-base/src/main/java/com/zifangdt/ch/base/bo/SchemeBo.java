package com.zifangdt.ch.base.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zifangdt.ch.base.dto.common.File;
import com.zifangdt.ch.base.dto.projectmanager.ConstructionProduct;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SchemeBo {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private String schemeDes;

    private String[] appendUrl;

    private String[] schemeDesignUrl;

    @NotNull
    private Long projectId;

    private List<ConstructionProduct> productList;

    private List<File> schemeDesignUrls;

    private List<File> appendUrls;

    private Long unitId;

    private Boolean assignShowOrNot;

    private Long productId;

    private ProcessOverview processOverview;

    private Long processId;
    private Long contractId;

    private String repertoryStatus;
    private String repertoryNum;
    private Long ticketId;

    private String unitName;

    public Boolean getAssignShowOrNot() {
        return assignShowOrNot;
    }

    public void setAssignShowOrNot(Boolean assignShowOrNot) {
        this.assignShowOrNot = assignShowOrNot;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProcessOverview getProcessOverview() {
        return processOverview;
    }

    public void setProcessOverview(ProcessOverview processOverview) {
        this.processOverview = processOverview;
    }

    public List<File> getSchemeDesignUrls() {
        return schemeDesignUrls;
    }

    public void setSchemeDesignUrls(List<File> schemeDesignUrls) {
        this.schemeDesignUrls = schemeDesignUrls;
    }

    public List<File> getAppendUrls() {
        return appendUrls;
    }

    public void setAppendUrls(List<File> appendUrls) {
        this.appendUrls = appendUrls;
    }



    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.schemeDes = schemeDes;
    }

    public String[] getAppendUrl() {
        return appendUrl;
    }

    public void setAppendUrl(String[] appendUrl) {
        this.appendUrl = appendUrl;
    }

    public String[] getSchemeDesignUrl() {
        return schemeDesignUrl;
    }

    public void setSchemeDesignUrl(String[] schemeDesignUrl) {
        this.schemeDesignUrl = schemeDesignUrl;
    }

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
}