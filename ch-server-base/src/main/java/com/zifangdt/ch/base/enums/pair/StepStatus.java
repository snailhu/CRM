package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.enums.ApproveBehavior;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum StepStatus implements PairedEnum {
    PENDING(1, "待处理"),
    RESOLVED(2, "已处理"),
    REJECTED(3, "已退回");

    final int intVerifier;
    final String name;

    StepStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public static StepStatus from(ApproveBehavior approveBehavior) {
        if (approveBehavior == ApproveBehavior.RESOLVE) {
            return StepStatus.RESOLVED;
        } else if (approveBehavior == ApproveBehavior.REJECT) {
            return StepStatus.REJECTED;
        } else {
            throw new RuntimeException();
        }
    }

}
