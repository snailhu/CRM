package com.zifangdt.ch.contract.bo;

import com.zifangdt.ch.base.dto.contract.Instalment;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.PayStyle;
import com.zifangdt.ch.base.validation.ConfiguredOption;
import com.zifangdt.ch.base.validation.PositiveNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public class CreateBo {
    @NotBlank
    private String printNumber;
    @NotEmpty
    @ConfiguredOption(ConfigType.contractTypes)
    private List<Long> types;
    @NotNull
    private Long customerId;
    private List<Long> bills;
    @NotNull
    @PositiveNumber
    private BigDecimal money;
    @NotNull
    private PayStyle payStyle;
    private String remark;
    private Boolean hasAdvance;
    @PositiveNumber
    private BigDecimal advance;
    @PositiveNumber
    private BigDecimal full;
    @Valid
    private List<Instalment> instalments;

    public BigDecimal getFull() {
        return full;
    }

    public void setFull(BigDecimal full) {
        this.full = full;
    }

    public Boolean getHasAdvance() {
        return hasAdvance;
    }

    public void setHasAdvance(Boolean hasAdvance) {
        this.hasAdvance = hasAdvance;
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

    public List<Instalment> getInstalments() {
        return instalments;
    }

    public void setInstalments(List<Instalment> instalments) {
        this.instalments = instalments;
    }
}
