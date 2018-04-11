package com.zifangdt.ch.base.enums.setting;

public enum CustomerSourceTypeEnum {
    Customer("客户"),
    Staff("公司人员"),
    Custom("自定义"),
    None("无");

    private final String name;

    CustomerSourceTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
