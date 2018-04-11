package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;
import org.jetbrains.annotations.Contract;

public enum OutStockType implements DisplayableEnum{
    Sell("销售出库"),
    Project("项目材料出库"),
    ProjectAuto("项目材料出库(自动)"),
    RepoExchange("调拨出库");

    private final String name;

    OutStockType(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
