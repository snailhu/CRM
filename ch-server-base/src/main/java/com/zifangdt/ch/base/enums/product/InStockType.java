package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;
import org.jetbrains.annotations.Contract;

public enum InStockType implements DisplayableEnum{
    Purchase("采购入库"),
    MaterialReturn("材料回收"),
    RepoExchange("仓库调拨");

    private final String name;

    InStockType(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
