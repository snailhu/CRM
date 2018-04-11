package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum PayStyle implements PairedEnum {
    INSTALMENT(1, "分期"),
    FULL(2, "一次性结清");

    final int intVerifier;
    final String name;

    PayStyle(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
