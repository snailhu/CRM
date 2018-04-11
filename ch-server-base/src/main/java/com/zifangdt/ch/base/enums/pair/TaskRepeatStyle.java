package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2017/11/6.
 */
public enum TaskRepeatStyle implements PairedEnum {
    NO(1, "不重复"),
    EVERY_DAY(2, "每天"),
    EVERY_WEEK(3, "每周"),
    EVERY_MONTH(4, "每月");

    final int intVerifier;
    final String name;

    TaskRepeatStyle(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }
}
