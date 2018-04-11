package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.product.OutStockStatus;
import com.zifangdt.ch.base.enums.product.OutStockType;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "out_stock_change")
public class OutStockChange extends AuditEntity<Long>{
    private Long repoId;

    private String stockNumber;

    private OutStockStatus stockStatus;

    private OutStockType outStockType;

    private Date stockTime;

    private String logisticNumber;

    private String logisticCompany;

    private Date deliverTime;

    private Long contractId;

    private Long projectId;

    private Long procedureId;

    private String customerName;

    private String customerAddress;

    private String customerContact;

    private BigDecimal logisticCost;

    private Long dealerId;

    private Long sellerUserId;

    private Integer price;

    private String relateName;

    private StockChangeItem[] items;

    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long operatorId; //出库人

    public Long getRepoId() {
        return repoId;
    }

    public OutStockChange setRepoId(Long repoId) {
        this.repoId = repoId;
        return this;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public OutStockChange setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
        return this;
    }

    public OutStockStatus getStockStatus() {
        return stockStatus;
    }

    public OutStockChange setStockStatus(OutStockStatus stockStatus) {
        this.stockStatus = stockStatus;
        return this;
    }

    public OutStockType getOutStockType() {
        return outStockType;
    }

    public OutStockChange setOutStockType(OutStockType outStockType) {
        this.outStockType = outStockType;
        return this;
    }

    public Date getStockTime() {
        return stockTime;
    }

    public OutStockChange setStockTime(Date stockTime) {
        this.stockTime = stockTime;
        return this;
    }

    public String getLogisticNumber() {
        return logisticNumber;
    }

    public OutStockChange setLogisticNumber(String logisticNumber) {
        this.logisticNumber = logisticNumber;
        return this;
    }

    public String getLogisticCompany() {
        return logisticCompany;
    }

    public OutStockChange setLogisticCompany(String logisticCompany) {
        this.logisticCompany = logisticCompany;
        return this;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public OutStockChange setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
        return this;
    }

    public Long getContractId() {
        return contractId;
    }

    public OutStockChange setContractId(Long contractId) {
        this.contractId = contractId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OutStockChange setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public OutStockChange setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        return this;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public OutStockChange setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
        return this;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public OutStockChange setDealerId(Long dealerId) {
        this.dealerId = dealerId;
        return this;
    }

    public Long getSellerUserId() {
        return sellerUserId;
    }

    public OutStockChange setSellerUserId(Long sellerUserId) {
        this.sellerUserId = sellerUserId;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public OutStockChange setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getRelateName() {
        return relateName;
    }

    public OutStockChange setRelateName(String relateName) {
        this.relateName = relateName;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public OutStockChange setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public OutStockChange setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
        return this;
    }

    public StockChangeItem[] getItems() {
        return items;
    }

    public OutStockChange setItems(StockChangeItem[] items) {
        this.items = items;
        return this;
    }

    public BigDecimal getLogisticCost() {
        return logisticCost;
    }

    public void setLogisticCost(BigDecimal logisticCost) {
        this.logisticCost = logisticCost;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}