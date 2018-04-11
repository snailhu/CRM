package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/13.
 */
public enum RevenueOrExpense implements PairedEnum {
    REVENUE(1, "收入"), EXPENSE(2, "支出");

    final int intVerifier;
    final String name;

    RevenueOrExpense(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
