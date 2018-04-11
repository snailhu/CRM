package com.zifangdt.ch.base.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.Bank;
import com.zifangdt.ch.base.enums.pair.Gender;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/24.
 */
@Table(name = "biz_partner")
public class Partner extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = 2146006732912992157L;

    @NotBlank
    private String name;
    @NotBlank
    private String company;
    @NotBlank
    private String contactPhone;
    @NamedProperty
    private Bank bank;
    private String accountName;
    private String bankNumber;
    private String remark;
    @NamedProperty
    private Gender gender;
    private Date birth;

    private String industry;
    private String address;
    private String qq;
    @Email
    private String email;
    private String createName;
    private Boolean deleted;

    @Transient
    @JsonIgnore
    private List<PartnerContribution> contributions;

    public Long getCustomerAmount() {
        return contributions == null ? null : (long) contributions.size();
    }

    public Long getContractAmount() {
        return contributions == null ? null : contributions.stream().flatMap(c -> c.getContractIds().stream()).count();
    }

    public List<PartnerContribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<PartnerContribution> contributions) {
        this.contributions = contributions;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
