package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;
import org.jetbrains.annotations.Contract;

public enum InStockStatus implements DisplayableEnum{
    InStock("已入库"),
    Draft("草稿");

    private final String name;

    InStockStatus(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
