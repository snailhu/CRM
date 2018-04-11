package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.product.PurchaseStatus;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "purchase")
public class Purchase extends AuditEntity<Long>{
    private String num;

    private String name;

    private BigDecimal totalAmount;

    private String remark;

    private String appendUrl;

    private Integer totalNum;

    private Integer inNum;

    private PurchaseStatus purchaseStatus;

    private Long providerId;

    private String comment;

    private Long processInstanceId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppendUrl() {
        return appendUrl;
    }

    public void setAppendUrl(String appendUrl) {
        this.appendUrl = appendUrl;
    }

    private PurchaseItem[] purchaseItems;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(PurchaseStatus status) {
        this.purchaseStatus = status;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Purchase setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public Long getProviderId() {
        return providerId;
    }

    public Purchase setProviderId(Long providerId) {
        this.providerId = providerId;
        return this;
    }

    public Integer getInNum() {
        return inNum;
    }

    public Purchase setInNum(Integer inNum) {
        this.inNum = inNum;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public Purchase setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public PurchaseItem[] getPurchaseItems() {
        return purchaseItems;
    }

    public Purchase setPurchaseItems(PurchaseItem[] purchaseItems) {
        this.purchaseItems = purchaseItems;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Purchase setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
}