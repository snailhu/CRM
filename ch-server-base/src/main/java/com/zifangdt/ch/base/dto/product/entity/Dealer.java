package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.validation.MobileNumber;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * 经销商
 */
@Table(name = "dealer")
public class Dealer extends AuditEntity<Long>{
    @NotNull
    private String name;

    @NotNull
    private String contact;

    @NotNull
    @MobileNumber
    private String phone;

    private String bank;

    private String bankAccountName;

    private String bankAccountNumber;

    private String remark;

    @NotNull
    private String address;

    public String getName() {
        return name;
    }

    public Dealer setName(String name) {
        this.name = name;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Dealer setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Dealer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public Dealer setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public Dealer setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        return this;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Dealer setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Dealer setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Dealer setAddress(String address) {
        this.address = address;
        return this;
    }
}