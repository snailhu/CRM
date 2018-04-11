package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum OrderSize implements PairedEnum {
    BIG(1, "大单"),
    NORMAL(2, "正常"),
    SMALL(3, "小单");

    final int intVerifier;
    final String name;

    OrderSize(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
