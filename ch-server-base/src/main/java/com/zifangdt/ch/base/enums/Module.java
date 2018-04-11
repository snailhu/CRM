package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2018/1/8.
 */
public enum Module {
    FINANCE(1<<4),
    CONTRACT(1),
    QUOTE(1<<1),
    PROJECTMANAGER(1<<2),
    PRODUCT(1<<3),
    CUSTOMER(1<<5),
    TICKET(1<<6);

    private int bitMask;

    Module(int bitMask){
        this.bitMask=bitMask;
    }

    public int getBitMask() {
        return bitMask;
    }
}
