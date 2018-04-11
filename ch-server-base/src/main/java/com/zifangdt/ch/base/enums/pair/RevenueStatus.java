package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/9.
 */
public enum RevenueStatus implements PairedEnum {
    PENDING(1, "待回款"),
    PROCESSED(2, "已回款"),
    INVALID(3, "失效");

    final int intVerifier;
    final String name;

    RevenueStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
