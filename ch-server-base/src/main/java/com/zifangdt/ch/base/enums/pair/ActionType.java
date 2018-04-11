package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/25.
 */
public enum ActionType implements PairedEnum {
    VISIT(1, "拜访"),
    CALL(2, "电联"),
    CARE(3, "关怀");

    final int intVerifier;
    final String name;

    ActionType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
