package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/19.
 */
public enum ProcessType implements PairedEnum {
    CONTRACT(1, "合同"),
    BILL(2, "报价单"),
    CONSTRUCT_SCHEME(5, "项目方案"),
    PURCHASE(100, "采购");

    final int intVerifier;
    final String name;

    ProcessType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
