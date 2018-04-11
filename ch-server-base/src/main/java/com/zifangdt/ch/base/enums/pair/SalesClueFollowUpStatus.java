package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum SalesClueFollowUpStatus implements PairedEnum {
    PENDING(1, "未处理"),
    PROCESSED(2, "已处理"),
    INVALID(3,"无效");

    final int intVerifier;
    final String name;

    SalesClueFollowUpStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
