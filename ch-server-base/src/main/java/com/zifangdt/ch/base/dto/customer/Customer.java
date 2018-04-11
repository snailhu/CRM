package com.zifangdt.ch.base.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.CustomerStage;
import com.zifangdt.ch.base.enums.pair.CustomerStatus;
import com.zifangdt.ch.base.enums.pair.CustomerWorth;
import com.zifangdt.ch.base.enums.pair.OrderSize;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/1/24.
 */
@Table(name = "biz_customer")
public class Customer extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -170776291858427207L;

    private String name;
    private String phone;
    private String address;
    private String houseNumber;
    @NamedProperty(target = JsonPropertyTarget.OPTION)
    private Long source;
    private Long sourceRelationId;
    private String remark;
    @NamedProperty
    private CustomerStage stage;
    private Long owner;
    private String ownerName;
    @NamedProperty
    private CustomerStatus status;

    private String tag;
    private String email;
    private String fax;
    private String secondaryContactName;
    private String secondaryContactPhone;
    private Boolean publicBlood;

    @NamedProperty
    private CustomerWorth worth;
    @NamedProperty
    private OrderSize orderSize;
    private Date predictedSignTime;
    private BigDecimal predictedMoney;
    private String invalidReason;

    @Transient
    private String sourceRelationName;

    @Transient
    @JsonIgnore
    private PartnerContribution contribution;

    @Transient
    private Boolean permittedToModify;
    @Transient
    private Boolean permittedToInvalid;

    @Transient
    private Boolean fromExcel;

    @Transient
    private Long countContracts;
    @Transient
    private Long countBills;
    @Transient
    private Long countProjects;
    @Transient
    private Long countHandledProjects;

    public Long getCountHandledProjects() {
        return countHandledProjects;
    }

    public void setCountHandledProjects(Long countHandledProjects) {
        this.countHandledProjects = countHandledProjects;
    }

    public Long getCountContracts() {
        return countContracts;
    }

    public void setCountContracts(Long countContracts) {
        this.countContracts = countContracts;
    }

    public Long getCountBills() {
        return countBills;
    }

    public void setCountBills(Long countBills) {
        this.countBills = countBills;
    }

    public Long getCountProjects() {
        return countProjects;
    }

    public void setCountProjects(Long countProjects) {
        this.countProjects = countProjects;
    }

    public Long getContractAmount() {
        return contribution == null ? null : (long) contribution.getContractIds().size();
    }

    public Boolean getFromExcel() {
        return fromExcel;
    }

    public void setFromExcel(Boolean fromExcel) {
        this.fromExcel = fromExcel;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public PartnerContribution getContribution() {
        return contribution;
    }

    public void setContribution(PartnerContribution contribution) {
        this.contribution = contribution;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getSourceRelationName() {
        return sourceRelationName;
    }

    public void setSourceRelationName(String sourceRelationName) {
        this.sourceRelationName = sourceRelationName;
    }

    public CustomerWorth getWorth() {
        return worth;
    }

    public void setWorth(CustomerWorth worth) {
        this.worth = worth;
    }

    public OrderSize getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(OrderSize orderSize) {
        this.orderSize = orderSize;
    }

    public Date getPredictedSignTime() {
        return predictedSignTime;
    }

    public void setPredictedSignTime(Date predictedSignTime) {
        this.predictedSignTime = predictedSignTime;
    }

    public BigDecimal getPredictedMoney() {
        return predictedMoney;
    }

    public void setPredictedMoney(BigDecimal predictedMoney) {
        this.predictedMoney = predictedMoney;
    }

    public Boolean getPublicBlood() {
        return publicBlood;
    }

    public void setPublicBlood(Boolean publicBlood) {
        this.publicBlood = publicBlood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getSourceRelationId() {
        return sourceRelationId;
    }

    public void setSourceRelationId(Long sourceRelationId) {
        this.sourceRelationId = sourceRelationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CustomerStage getStage() {
        return stage;
    }

    public void setStage(CustomerStage stage) {
        this.stage = stage;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSecondaryContactName() {
        return secondaryContactName;
    }

    public void setSecondaryContactName(String secondaryContactName) {
        this.secondaryContactName = secondaryContactName;
    }

    public String getSecondaryContactPhone() {
        return secondaryContactPhone;
    }

    public void setSecondaryContactPhone(String secondaryContactPhone) {
        this.secondaryContactPhone = secondaryContactPhone;
    }
}
