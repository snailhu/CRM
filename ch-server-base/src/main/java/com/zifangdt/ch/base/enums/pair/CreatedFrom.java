package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum CreatedFrom implements PairedEnum {
    CONTRACT(1, "合同"),
    CUSTOMER(2, "客户"),
    CLUE(3, "销售线索"),
    PROJECT(4,"项目");

    final int intVerifier;
    final String name;

    CreatedFrom(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
