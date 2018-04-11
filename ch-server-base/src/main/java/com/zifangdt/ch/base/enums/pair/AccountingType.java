package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum AccountingType implements PairedEnum {
    NORMAL(1, "正常结算"),
    FLOOR(2, "核算价低于底价"),
    OFFER(3, "特价项目"),
    FLOOR_AND_OFFER(4,"低于底价特价项目");

    final int intVerifier;
    final String name;

    AccountingType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
