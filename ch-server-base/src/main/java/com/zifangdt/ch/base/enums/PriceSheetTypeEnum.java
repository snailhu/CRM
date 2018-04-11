package com.zifangdt.ch.base.enums;

public enum PriceSheetTypeEnum {
    Design("设计报价单"),
    NonDesign("非设计报价单");

    private final String name;

    PriceSheetTypeEnum(String name) {
        this.name = name;
    }
}
