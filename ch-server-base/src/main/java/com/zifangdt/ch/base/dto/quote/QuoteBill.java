package com.zifangdt.ch.base.dto.quote;

import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.dto.common.File;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class QuoteBill extends BaseEntity<Long> {

    private String billNum;

    private String billName;

    private BigDecimal billTotalPrice;

    private Long createUserId;

    private String createUserName;

    private Long ownerId;

    private String ownerName;

    private Long customerId;

    private String customerName;

    private String customerAddress;

    private String contactPerson;

    private String environmentDes;

    private String contactTel;

    private Date deadline;

    private String customerRequirement;

    private Integer status;
    private String statusName;

    private String description;

    private String requireImgurl;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;

    private Long processId;

    private ProcessOverview processOverview;


    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public ProcessOverview getProcessOverview() {
        return processOverview;
    }

    public void setProcessOverview(ProcessOverview processOverview) {
        this.processOverview = processOverview;
    }

    private List<File> requireImgUrls;


    public List<File> getRequireImgUrls() {
        return requireImgUrls;
    }

    public void setRequireImgUrls(List<File> requireImgUrls) {
        this.requireImgUrls = requireImgUrls;
    }

    private List<QuoteProduct> productList;

    public List<QuoteProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<QuoteProduct> productList) {
        this.productList = productList;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private String contactPortrait;


    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum == null ? null : billNum.trim();
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    public BigDecimal getBillTotalPrice() {
        return billTotalPrice;
    }

    public void setBillTotalPrice(BigDecimal billTotalPrice) {
        this.billTotalPrice = billTotalPrice;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress == null ? null : customerAddress.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getCustomerRequirement() {
        return customerRequirement;
    }

    public void setCustomerRequirement(String customerRequirement) {
        this.customerRequirement = customerRequirement == null ? null : customerRequirement.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getEnvironmentDes() {
        return environmentDes;
    }

    public void setEnvironmentDes(String environmentDes) {
        this.environmentDes = environmentDes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRequireImgurl() {
        return requireImgurl;
    }

    public void setRequireImgurl(String requireImgurl) {
        this.requireImgurl = requireImgurl == null ? null : requireImgurl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getContactPortrait() {
        return contactPortrait;
    }

    public void setContactPortrait(String contactPortrait) {
        this.contactPortrait = contactPortrait;
    }
}