package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/10/18.
 */
public enum NoticeTag {
    CUSTOMER("客户"),
    DAILY("日报"),
    CONTRACT("合同"),
    BILL("报价单"),
    PROCESS("流程"),
    CLUE("线索"),
    MARKET_ACTIVITY("市场活动"),
    IM("IM"),
    ;

    private String displayName;
    NoticeTag(String displayName){
        this.displayName=displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
