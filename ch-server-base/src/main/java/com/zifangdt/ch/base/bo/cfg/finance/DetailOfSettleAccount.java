package com.zifangdt.ch.base.bo.cfg.finance;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.AccountUse;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/8.
 */
public class DetailOfSettleAccount extends DetailOfOption {
    private String accountNumber;
    private String owner;
    @NamedProperty
    private AccountUse use;
    private BigDecimal initialMoney;

    public BigDecimal getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(BigDecimal initialMoney) {
        this.initialMoney = initialMoney;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public AccountUse getUse() {
        return use;
    }

    public void setUse(AccountUse use) {
        this.use = use;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (use == null) {
            throw new DataInvalidException("必须指定结算账户适用于哪类账户");
        }
    }
}
