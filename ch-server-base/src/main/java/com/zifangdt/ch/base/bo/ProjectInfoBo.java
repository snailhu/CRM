package com.zifangdt.ch.base.bo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProjectInfoBo {

    @NotNull(message="合同Id不能为空")
    private Long contractId;

//    @NotBlank(message="客户名称不能为空")
    private String customer;

    private Long customerId;

    @NotBlank(message="合同名称不能为空")
    private String contractName;

    @NotNull(message="合同金额为空")
    private Double contractMoney;

    @NotBlank(message="项目施工地址不能为空")
    private String projectAddress;

//    @NotBlank(message="客户联系电话")
    private String customerTel;

    //合同所有者名称（所有者）
    @NotBlank(message="所有者姓名不能为空")
    private String owner;
    //合同所有者Id（所有者）
    @NotNull
    private Long ownerId;

    //合同编号
    private String contractNum;

    //合同类型
    private List<ContractTypeBo> contractTypes;

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

    public String getOwner() {
        return owner;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
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

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public List<ContractTypeBo> getContractTypes() {
        return contractTypes;
    }

    public void setContractTypes(List<ContractTypeBo> contractTypes) {
        this.contractTypes = contractTypes;
    }
}