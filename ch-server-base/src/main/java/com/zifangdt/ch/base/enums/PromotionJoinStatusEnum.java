package com.zifangdt.ch.base.enums;

public enum PromotionJoinStatusEnum {
    Unhandled("待处理"),
    AddToCustomer("加为客户"),
    AddToTodo("加为待办"),
    Invalid("无效");

    private final String name;

    PromotionJoinStatusEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
