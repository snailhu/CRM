package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/10/26.
 */
public enum ApprovalType {
    BILL("报价单"),
    CONTRACT("合同"),
    MATERIAL("项目材料"),
    CONSTRUCT("施工"),
    PURCHASE("采购")
    ;

    ApprovalType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
