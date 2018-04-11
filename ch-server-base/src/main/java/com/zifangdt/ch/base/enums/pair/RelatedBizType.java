package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2017/10/25.
 */
public enum RelatedBizType implements PairedEnum {
    PROJECT(2, "项目"), CUSTOMER(3, "客户"), CONTRACT(1, "合同");

    final int intVerifier;
    final String name;

    RelatedBizType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
