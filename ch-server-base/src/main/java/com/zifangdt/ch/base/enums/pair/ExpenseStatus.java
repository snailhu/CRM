package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/9.
 */
public enum ExpenseStatus implements PairedEnum {
    PENDING(1, "待支付"),
    PROCESSED(2, "已支付"),
    INVALID(3, "失效");

    final int intVerifier;
    final String name;

    ExpenseStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }
}
