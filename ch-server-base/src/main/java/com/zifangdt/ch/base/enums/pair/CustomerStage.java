package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum CustomerStage implements PairedEnum {
    APPROACH(1, "初步接洽"),
    CONFIRM(2, "需求确认"),
    QUOTE(3, "方案报价"),
    NEGOTIATE(4, "谈判审批");

    final int intVerifier;
    final String name;

    CustomerStage(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
