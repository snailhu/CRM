package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum CustomerWorth implements PairedEnum {
    GOOD(1, "有价值"),
    BAD(2, "无价值"),
    NONE(3, "不确定");

    final int intVerifier;
    final String name;

    CustomerWorth(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
