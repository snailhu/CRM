package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2017/10/31.
 */
public enum SystemLogEvent implements PairedEnum {
    REGISTER(1, "注册"),
    LAUNCH(2, "启动"),
    LOGIN(3, "登录");

    final int intVerifier;
    final String name;

    SystemLogEvent(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }
}
