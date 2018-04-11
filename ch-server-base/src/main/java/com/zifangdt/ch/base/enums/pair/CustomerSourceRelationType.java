package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum CustomerSourceRelationType implements PairedEnum {
    CREATOR_CUSTOMIZATION(0, "创建者自定义"),
    CUSTOMER(1, "客户"),
    COMPANY_STAFF(2, "公司人员"),
    CUSTOMIZED_DATA(3, "自定义数据"),
    NONE(4, "无"),
    CLUE(5, "对应的销售线索"),
    MARKET(6, "对应的市场活动");

    final int intVerifier;
    final String name;

    CustomerSourceRelationType(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
