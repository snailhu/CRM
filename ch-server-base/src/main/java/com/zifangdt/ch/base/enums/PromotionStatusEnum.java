package com.zifangdt.ch.base.enums;

public enum PromotionStatusEnum {
    UnPublish("未发布"),
    OnGoing("进行中"),
    Canceled("已取消");

    private final String name;
    PromotionStatusEnum(String name){
        this.name = name;
    }
}
