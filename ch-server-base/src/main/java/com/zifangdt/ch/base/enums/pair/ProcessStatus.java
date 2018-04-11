package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum ProcessStatus implements PairedEnum {
    PENDING(1, "待审批"),
    APPROVING(2, "审批中"),
    APPROVED(3, "已审批"),
    CANCELED(4, "已取消");

    final int intVerifier;
    final String name;

    ProcessStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
