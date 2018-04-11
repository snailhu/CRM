package com.zifangdt.ch.base.bo;

import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/4/10.
 */
public class IdAndNameOfAccount extends IdAndName {
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
