package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum TaskUrgency implements PairedEnum {
    URGENT(1, "紧急"),
    IMPORTANT(2, "重要"),
    NORMAL(3, "正常"),
    LOW(4,"低");

    final int intVerifier;
    final String name;

    TaskUrgency(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public String getUrgencyName() {
        return this.name;
    }
}
