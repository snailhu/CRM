package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum Gender implements PairedEnum {
    MALE(1, "男"), FEMALE(2, "女");

    final int intVerifier;
    final String name;

    Gender(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
