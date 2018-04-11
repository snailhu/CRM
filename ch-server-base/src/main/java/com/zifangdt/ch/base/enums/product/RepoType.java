package com.zifangdt.ch.base.enums.product;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;

public enum RepoType implements DisplayableEnum{
    Normal("正品仓"),
    Defect("次品仓");
    private final String name;

    RepoType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
