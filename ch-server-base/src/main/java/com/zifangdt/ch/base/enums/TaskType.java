package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/11/6.
 */
public enum TaskType {
    PROCESS("流程"),
    SUB_TASK("任务"),
    TODO("待办"),
    WORK_ORDER("工单");

    TaskType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
