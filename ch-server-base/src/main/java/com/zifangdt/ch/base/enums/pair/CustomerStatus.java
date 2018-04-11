package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum CustomerStatus implements PairedEnum {
    POTENTIAL(1, "潜在客户"),
    SIGNED(2, "签约客户"),
    INVALID(3, "失效客户");

    final int intVerifier;
    final String name;

    CustomerStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
