package com.zifangdt.ch.base.enums;

public enum PromotionTypeEnum {
    SignUp("报名"),
    Order("在线订单");

    private final String name;

    PromotionTypeEnum(String name) {
        this.name = name;
    }
}
