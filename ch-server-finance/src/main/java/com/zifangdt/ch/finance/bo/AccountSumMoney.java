package com.zifangdt.ch.finance.bo;

import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public class AccountSumMoney {
    private long countEnabled;
    private BigDecimal sumEnabled;
    private long countDisabled;
    private BigDecimal sumDisabled;

    public long getCountEnabled() {
        return countEnabled;
    }

    public void setCountEnabled(long countEnabled) {
        this.countEnabled = countEnabled;
    }

    public BigDecimal getSumEnabled() {
        return sumEnabled;
    }

    public void setSumEnabled(BigDecimal sumEnabled) {
        this.sumEnabled = sumEnabled;
    }

    public long getCountDisabled() {
        return countDisabled;
    }

    public void setCountDisabled(long countDisabled) {
        this.countDisabled = countDisabled;
    }

    public BigDecimal getSumDisabled() {
        return sumDisabled;
    }

    public void setSumDisabled(BigDecimal sumDisabled) {
        this.sumDisabled = sumDisabled;
    }
}
