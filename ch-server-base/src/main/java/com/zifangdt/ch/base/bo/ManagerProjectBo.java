package com.zifangdt.ch.base.bo;


import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.pair.TaskStatus;

import java.util.Date;

public class ManagerProjectBo {

    private Long id;

    private String projectName;

    private String customer;

    private String contractName;

    //合同类型
    private String contractType;

    //工期类型
    private Long constructionId;
    private Long constructionTypeId;
    private String construction;

    //工序名称
    private String constructionUnit;
    private Long constructionUnitId;
    //配置中生成的Id
    private Long unitTypeId;

    private String statusName;

    private String projectAddress;

    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Double contractMoney;
    private String projectManager;
    private String sale;

    private Long unitId;
    private String unitName;

    @NamedProperty
    private TaskStatus.InventoryStatus inventoryStatus;
    private Long contractId;

    private String customerTel;

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public TaskStatus.InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(TaskStatus.InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getConstructionTypeId() {
        return constructionTypeId;
    }

    public void setConstructionTypeId(Long constructionTypeId) {
        this.constructionTypeId = constructionTypeId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public Long getConstructionUnitId() {
        return constructionUnitId;
    }

    public void setConstructionUnitId(Long constructionUnitId) {
        this.constructionUnitId = constructionUnitId;
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
        this.projectName = projectName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }
}