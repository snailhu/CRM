package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.pair.TaskStatus;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ManagerProject {
    private Long id;

    private String projectName;

    @NotNull(message="合同Id不能为空")
    private Long contractId;
    private Long version;

    private String contactPerson;

    private String creator;

    private String projectManager;

    private Integer status;

    @NotBlank(message="客户名称不能为空")
    private String customer;

    private Long customerId;

    private Long projectManagerId;

    @NotBlank(message="合同名称不能为空")
    private String contractName;

    private Date createTime;
    private Date endTime;

    @NotNull(message="合同金额为空")
    private Double contractMoney;

    private String contractType;

    private Date startTime;

    private String attribute;
    private String statusName;
    private String contractNum;

    private String customerTel;
    @NotBlank(message="项目施工地址不能为空")
    private String projectAddress;

    private String obtainStatus;

    private String owner;
    @NamedProperty
    private TaskStatus.InventoryStatus inventoryStatus;
    @NamedProperty
    private TaskStatus.ProjectAccountStatus accountStatus;
    private Long ownerId;

    private Double income;
    private Double selfCost;
    private Double profit;
    private Double payOut;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public TaskStatus.InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(TaskStatus.InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public TaskStatus.ProjectAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(TaskStatus.ProjectAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getSelfCost() {
        return selfCost;
    }

    public void setSelfCost(Double selfCost) {
        this.selfCost = selfCost;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getPayOut() {
        return payOut;
    }

    public void setPayOut(Double payOut) {
        this.payOut = payOut;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getObtainStatus() {
        return obtainStatus;
    }

    public void setObtainStatus(String obtainStatus) {
        this.obtainStatus = obtainStatus;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }
}