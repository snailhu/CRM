package com.zifangdt.ch.base.enums.ticket;

public enum WorkloadType implements DisplayableEnum{
    LU("路"), ZU("组"), JUAN("卷"), TAO("套"), GE("个"), KOU("口"), TAI("台"), CHUKOU("出口"), QITA("其他");
    private final String name;
    WorkloadType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
