package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum InvoiceType implements PairedEnum {
    REVENUE(1, "增值税专用发票"),
    EXPENSE(2, "普通发票");

    final int intVerifier;
    final String name;

    InvoiceType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
