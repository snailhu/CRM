package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum TaskType implements PairedEnum {
    PROCESS(1, "流程"),
    SUB_TASK(2, "子任务"),
    TODO(3, "待办"),
    WORK_ORDER(4, "工单");

    final int intVerifier;
    final String name;

    TaskType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
