package com.zifangdt.ch.base.enums;

public enum StockChangeType {
    In("入库"),
    Out("出库");

    private final String name;

    StockChangeType(String name) {
        this.name = name;
    }
}
