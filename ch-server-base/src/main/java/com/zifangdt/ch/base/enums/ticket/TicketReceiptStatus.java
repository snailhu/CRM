package com.zifangdt.ch.base.enums.ticket;

public enum TicketReceiptStatus implements DisplayableEnum{
    CREATE("创建回执"), REJECT("拒绝回执"), CONFIRM("确认回执");

    private final String name;
    TicketReceiptStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
