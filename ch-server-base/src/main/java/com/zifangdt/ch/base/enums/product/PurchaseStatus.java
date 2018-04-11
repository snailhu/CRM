package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;

public enum PurchaseStatus implements DisplayableEnum{
    NotStart("未开始,等待审核"),
    Finish("已完成"),
    OnGoing("进行中"),
    Cancel("已取消");
    private final String name;

    PurchaseStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
