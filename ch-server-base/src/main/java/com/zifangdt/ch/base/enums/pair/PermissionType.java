package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum PermissionType implements PairedEnum {
    REQUEST(1, "请求权限"),
    DATA(2, "数据权限");

    final int intVerifier;
    final String name;

    PermissionType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
