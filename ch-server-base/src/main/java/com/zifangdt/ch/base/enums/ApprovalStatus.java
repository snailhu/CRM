package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/10/26.
 */
public enum ApprovalStatus {
    PENDING("待审批"),
    APPROVING("审批中"),
    APPROVED("已审批"),
    CANCELED("已取消");

    ApprovalStatus(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
