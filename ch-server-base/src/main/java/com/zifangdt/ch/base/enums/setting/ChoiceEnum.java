package com.zifangdt.ch.base.enums.setting;

import java.util.Arrays;

/**
 * uaa service 多选1 的选项
 */
public enum ChoiceEnum {
    On("开启"),
    Off("关闭"),

    UsernameLogin("仅使用用户名登录"),
    PhoneLogin("仅使用手机登录"),
    HybridLogin("同时支持用户名和手机登录"),

    FixPassword("固定密码"),
    RandPassword("随机密码"),

    // project
    ContractCreate("合同创建时"),
    ContractPassPermit("合同审核通过时");

    private String codename;
    private ChoiceEnum(String codename){
        this.codename = codename;
    }

    public String getCodename() {
        return codename;
    }

    public static ChoiceEnum[] getBoolChoice(){
        return new ChoiceEnum[]{On, Off};
    }
}
