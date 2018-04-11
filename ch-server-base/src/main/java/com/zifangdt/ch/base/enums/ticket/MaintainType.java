package com.zifangdt.ch.base.enums.ticket;

public enum MaintainType implements DisplayableEnum{
    WARRANTY("维保期内"), WARRANTY_EXPIRE("维保期外");

    private final String name;

    MaintainType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
