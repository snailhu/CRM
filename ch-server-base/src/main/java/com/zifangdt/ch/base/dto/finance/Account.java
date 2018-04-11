package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.validation.ConfiguredOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/4/2.
 */
@Table(name = "biz_account")
public class Account extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -8978365356788222921L;

    @NotBlank
    private String name;
    private String number;
    private String owner;
    @NotNull
    @NamedProperty
    @ConfiguredOption(ConfigType.paymentWays)
    private Long paymentWay;
    private BigDecimal balance;
    private String remark;
    private Boolean disabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Long paymentWay) {
        this.paymentWay = paymentWay;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
