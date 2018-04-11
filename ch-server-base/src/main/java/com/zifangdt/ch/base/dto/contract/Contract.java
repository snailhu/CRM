package com.zifangdt.ch.base.dto.contract;

import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.PayStyle;
import com.zifangdt.ch.base.validation.ConfiguredOption;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_contract")
public class Contract extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -1503341309693130310L;

    private String number;
    private String printNumber;
    @ConfiguredOption(ConfigType.contractTypes)
    @VerboseProperty(target = JsonPropertyTarget.OPTION)
    private List<Long> types;
    private Long customerId;
    private Long customerSourceRelationId;
    @Transient
    private Map<String, Object> customerDetail;
    @VerboseProperty(target = JsonPropertyTarget.BILL)
    private List<Long> bills;
    private BigDecimal money;
    @NamedProperty
    private PayStyle payStyle;
    private String remark;
    private BigDecimal advance;
    private BigDecimal full;
    private Date signTime;
    private Long processId;
    private String invalidReason;
    private Boolean invalidated;
    private Long projectId;
    private String customerName;
    private String billNames;

    @Transient
    private List<Instalment> instalments;

    @Transient
    private ProductDetail productDetail;
    @Transient
    private Accounting1 accounting1;
    @Transient
    private Accounting2 accounting2;

    private Long owner;
    private String ownerName;
    private Boolean hasAdvance;

    @Transient
    private String ownerOrganization;
    @Transient
    private ProcessInstance processInstance;

    @Transient
    private ProcessOverview processOverview;
    @Transient
    private List<Journal> periodRecords;

    @Transient
    private Boolean permittedToModify;
    @Transient
    private Boolean permittedToInvalid;
    @Transient
    private Boolean permittedToAddRevenueRecord;
    @Transient
    private String customerSourceRelationName;

    public String getCustomerSourceRelationName() {
        return customerSourceRelationName;
    }

    public void setCustomerSourceRelationName(String customerSourceRelationName) {
        this.customerSourceRelationName = customerSourceRelationName;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public Map<String, Object> getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(Map<String, Object> customerDetail) {
        this.customerDetail = customerDetail;
    }

    public Boolean getPermittedToModify() {
        return permittedToModify;
    }

    public void setPermittedToModify(Boolean permittedToModify) {
        this.permittedToModify = permittedToModify;
    }

    public Boolean getPermittedToInvalid() {
        return permittedToInvalid;
    }

    public void setPermittedToInvalid(Boolean permittedToInvalid) {
        this.permittedToInvalid = permittedToInvalid;
    }

    public Boolean getPermittedToAddRevenueRecord() {
        return permittedToAddRevenueRecord;
    }

    public void setPermittedToAddRevenueRecord(Boolean permittedToAddRevenueRecord) {
        this.permittedToAddRevenueRecord = permittedToAddRevenueRecord;
    }

    public List<Journal> getPeriodRecords() {
        return periodRecords;
    }

    public void setPeriodRecords(List<Journal> periodRecords) {
        this.periodRecords = periodRecords;
    }

    public String getBillNames() {
        return billNames;
    }

    public void setBillNames(String billNames) {
        this.billNames = billNames;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getRoyaltyMoney() {
        return accounting2 == null || accounting2.getRoyaltyRate() == null ? null : money.multiply(BigDecimal.valueOf(accounting2.getRoyaltyRate())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCustomerSourceRelationId() {
        return customerSourceRelationId;
    }

    public void setCustomerSourceRelationId(Long customerSourceRelationId) {
        this.customerSourceRelationId = customerSourceRelationId;
    }

    public Boolean getInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        this.invalidated = invalidated;
    }

    public BigDecimal getFull() {
        return full;
    }

    public void setFull(BigDecimal full) {
        this.full = full;
    }

    public ProcessOverview getProcessOverview() {
        return processOverview;
    }

    public void setProcessOverview(ProcessOverview processOverview) {
        this.processOverview = processOverview;
    }

    public Boolean getHasAdvance() {
        return hasAdvance;
    }

    public void setHasAdvance(Boolean hasAdvance) {
        this.hasAdvance = hasAdvance;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerOrganization() {
        return ownerOrganization;
    }

    public void setOwnerOrganization(String ownerOrganization) {
        this.ownerOrganization = ownerOrganization;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public List<Instalment> getInstalments() {
        return instalments;
    }

    public void setInstalments(List<Instalment> instalments) {
        this.instalments = instalments;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public Accounting1 getAccounting1() {
        return accounting1;
    }

    public void setAccounting1(Accounting1 accounting1) {
        this.accounting1 = accounting1;
    }

    public Accounting2 getAccounting2() {
        return accounting2;
    }

    public void setAccounting2(Accounting2 accounting2) {
        this.accounting2 = accounting2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(String printNumber) {
        this.printNumber = printNumber;
    }

    public List<Long> getTypes() {
        return types;
    }

    public void setTypes(List<Long> types) {
        this.types = types;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getBills() {
        return bills;
    }

    public void setBills(List<Long> bills) {
        this.bills = bills;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public PayStyle getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(PayStyle payStyle) {
        this.payStyle = payStyle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

}
