package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum AccountUse implements PairedEnum {
    REVENUE(1, "收款账户"),
    EXPENSE(2, "付款账户"),
    BOTH(3, "收款账户,付款账户");

    final int intVerifier;
    final String name;

    AccountUse(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
