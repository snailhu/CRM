package com.zifangdt.ch.base.enums.ticket;

public enum TicketTypeSource implements DisplayableEnum{
    CONTRACT_TYPE("合同类型"),
    STAFF_FEEDBACK("内部反馈"),

    APP("APP"),
    PHONE("电话"),
    OTHER("其他");

    private final String name;

    TicketTypeSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
