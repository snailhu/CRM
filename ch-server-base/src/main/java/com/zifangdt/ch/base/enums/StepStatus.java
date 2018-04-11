package com.zifangdt.ch.base.enums;

import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;

/**
 * Created by 袁兵 on 2017/10/13.
 */
public enum StepStatus implements IntVerifierEnum {
    PENDING(0,"待处理"), PROCESSED(1,"已处理"), RETURNED(2,"已退回");

    private String statusName;
    private int value;

    StepStatus(int value,String statusName) {
        this.value=value;
        this.statusName = statusName;
    }

    @Override
    public int getIntVerifier() {
        return value;
    }

    public String getStatusName() {
        return statusName;
    }

}
