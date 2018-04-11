package com.zifangdt.ch.base.enums.ticket;

public enum SatisfactionLevel implements DisplayableEnum{

    SATISFY("满意"), GENERAL("一般"), DISCONTENT("不满意");
    private final String name;
    SatisfactionLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
