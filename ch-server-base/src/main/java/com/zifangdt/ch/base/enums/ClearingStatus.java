package com.zifangdt.ch.base.enums;

import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;

/**
 * Created by 袁兵 on 2018/2/7.
 */
public enum ClearingStatus implements DisplayableEnum {
    PENDING("待结算"), APPROVING("结算中"), APPROVED("已结算");

    private final String name;

    ClearingStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
