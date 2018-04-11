package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum SalesClueStatus implements PairedEnum {
    PUBLISHED(1, "已发布"),
    CLOSED(2, "已关闭");

    final int intVerifier;
    final String name;

    SalesClueStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
