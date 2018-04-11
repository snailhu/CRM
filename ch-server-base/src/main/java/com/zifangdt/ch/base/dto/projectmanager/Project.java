package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Project {
    private Long id;

    @NotNull
    private String projectName;

    @NotNull
    private Long contractId;

    private Date startTime;

    private Date endTime;

    @NotNull
    private String contactPerson;

    @NotNull
    private String creator;

    private String projectManager;

    private String projectAddress;

    private Integer status;
    @NotNull
    private String contractType;
    @NotNull
    private Double contractMoney;


    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    private Integer projectStage;

    private String stageName;

    private String customer;

    private String contactTel;

    private Double projectArea;

    private Long projectManagerId;

    private String contractOwner;

    private Long contractOwnerId;

    private String remark;

    private String appendUrl;

    @NotNull
    private String contractName;

    private Date contractStartTime;
    private Date createTime;
    private Date updateTime;

    private Date contractEndTime;

    private Integer contractPayType;

    private String contractPayTypeName;

    private String contractCustomerName;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager == null ? null : projectManager.trim();
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress == null ? null : projectAddress.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Integer projectStage) {
        this.projectStage = projectStage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName == null ? null : stageName.trim();
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    public Double getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(Double projectArea) {
        this.projectArea = projectArea;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getContractOwner() {
        return contractOwner;
    }

    public void setContractOwner(String contractOwner) {
        this.contractOwner = contractOwner == null ? null : contractOwner.trim();
    }

    public Long getContractOwnerId() {
        return contractOwnerId;
    }

    public void setContractOwnerId(Long contractOwnerId) {
        this.contractOwnerId = contractOwnerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAppendUrl() {
        return appendUrl;
    }

    public void setAppendUrl(String appendUrl) {
        this.appendUrl = appendUrl;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Integer getContractPayType() {
        return contractPayType;
    }

    public void setContractPayType(Integer contractPayType) {
        this.contractPayType = contractPayType;
    }

    public String getContractPayTypeName() {
        return contractPayTypeName;
    }

    public void setContractPayTypeName(String contractPayTypeName) {
        this.contractPayTypeName = contractPayTypeName == null ? null : contractPayTypeName.trim();
    }

    public String getContractCustomerName() {
        return contractCustomerName;
    }

    public void setContractCustomerName(String contractCustomerName) {
        this.contractCustomerName = contractCustomerName == null ? null : contractCustomerName.trim();
    }
}