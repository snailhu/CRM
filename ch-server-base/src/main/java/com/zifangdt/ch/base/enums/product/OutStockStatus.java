package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;
import org.jetbrains.annotations.Contract;

public enum OutStockStatus implements DisplayableEnum{
    PreOutStock("待出库"),
    OutStock("已出库"),
    Draft("草稿");

    private final String name;

    OutStockStatus(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
