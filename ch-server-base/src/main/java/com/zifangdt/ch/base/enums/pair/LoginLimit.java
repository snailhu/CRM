package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/26.
 */
public enum LoginLimit implements PairedEnum {
    NONE(1, "不限制登录"),
    MOBILE_LIMITED(2, "限制移动端"),
    WEB_LIMITED(3, "限制web端");

    final int intVerifier;
    final String name;

    LoginLimit(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public static LoginLimit of(String name) {
        for (LoginLimit u : values()) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        throw new IllegalArgumentException();
    }
}
