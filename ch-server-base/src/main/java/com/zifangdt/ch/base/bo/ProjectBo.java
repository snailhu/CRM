package com.zifangdt.ch.base.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zifangdt.ch.base.dto.common.File;
import com.zifangdt.ch.base.validation.MobileNumber;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ProjectBo {

    private boolean permitEdit;

    private boolean permitOperate;

    private String projectManager;

    private Integer status;

    private String statusName;

    private Double projectArea;

    private Long projectManagerId;

    private String contractOwner;

    private Long contractOwnerId;

    private String remark;

    private String[] appendUrls;

    private List<File> appendUrlsDetail;

    private String contractName;

    private Date contractStartTime;

    private Date contractEndTime;

    private Integer contractPayType;

    private String contractPayTypeName;

    private String contractCustomerName;
    @NotNull
    private String projectName;
    private Long contractId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private Date createTime;
    private Date updateTime;

    private String contactPerson;

    private String creator;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private long projectId;

    private String projectAddress;

    private Integer projectStage;

    private String stageName;

    private String customer;

    private Double contractMoney;

    private String contractType;
    private String contractTypeName;

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public boolean isPermitEdit() {
        return permitEdit;
    }

    public void setPermitEdit(boolean permitEdit) {
        this.permitEdit = permitEdit;
    }

    public boolean isPermitOperate() {
        return permitOperate;
    }

    public void setPermitOperate(boolean permitOperate) {
        this.permitOperate = permitOperate;
    }

    public List<File> getAppendUrlsDetail() {
        return appendUrlsDetail;
    }

    public void setAppendUrlsDetail(List<File> appendUrlsDetail) {
        this.appendUrlsDetail = appendUrlsDetail;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    private List<MaterialTypeBo> materialTypeBos;


    @NotNull
    @MobileNumber
    private String contactTel;

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

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.contractOwner = contractOwner;
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
        this.remark = remark;
    }

    public String[] getAppendUrls() {
        return appendUrls;
    }

    public void setAppendUrls(String[] appendUrls) {
        this.appendUrls = appendUrls;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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
        this.contractPayTypeName = contractPayTypeName;
    }

    public String getContractCustomerName() {
        return contractCustomerName;
    }

    public void setContractCustomerName(String contractCustomerName) {
        this.contractCustomerName = contractCustomerName;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<MaterialTypeBo> getMaterialTypeBos() {
        return materialTypeBos;
    }

    public void setMaterialTypeBos(List<MaterialTypeBo> materialTypeBos) {
        this.materialTypeBos = materialTypeBos;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
        this.contactPerson = contactPerson;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
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
        this.stageName = stageName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}