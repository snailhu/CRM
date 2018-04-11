package com.zifangdt.ch.contract.bo;

import com.zifangdt.ch.base.dto.contract.Accounting1;
import com.zifangdt.ch.base.dto.contract.Accounting2;
import com.zifangdt.ch.base.dto.contract.Instalment;
import com.zifangdt.ch.base.enums.pair.PayStyle;
import com.zifangdt.ch.base.validation.PositiveNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public class ApproveFinanceBo {
    private Long customerSourceRelationId;
    @NotNull
    private Date signTime;
    @NotBlank
    private String number;
    @NotNull
    @PositiveNumber
    private BigDecimal money;

    @Valid
    @NotNull
    private Accounting1 accounting1;
    @Valid
    @NotNull
    private Accounting2 accounting2;

    @NotNull
    private PayStyle payStyle;
    @PositiveNumber
    private BigDecimal advance;
    @Valid
    private List<Instalment> instalments;
    private Boolean hasAdvance;
    @PositiveNumber
    private BigDecimal full;

    public Long getCustomerSourceRelationId() {
        return customerSourceRelationId;
    }

    public void setCustomerSourceRelationId(Long customerSourceRelationId) {
        this.customerSourceRelationId = customerSourceRelationId;
    }

    public BigDecimal getFull() {
        return full;
    }

    public void setFull(BigDecimal full) {
        this.full = full;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public PayStyle getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(PayStyle payStyle) {
        this.payStyle = payStyle;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

    public List<Instalment> getInstalments() {
        return instalments;
    }

    public void setInstalments(List<Instalment> instalments) {
        this.instalments = instalments;
    }

    public Boolean getHasAdvance() {
        return hasAdvance;
    }

    public void setHasAdvance(Boolean hasAdvance) {
        this.hasAdvance = hasAdvance;
    }
}
