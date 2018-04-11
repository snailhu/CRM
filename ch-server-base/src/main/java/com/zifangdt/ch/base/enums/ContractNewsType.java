package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/10/11.
 */
public enum ContractNewsType implements NewsType {
    CONTRACT_CREATED("#operator#创建了合同#contract#"),
    CONTRACT_BASIC_INFO_MODIFIED("#operator#修改了合同#contract#的基本信息"),
    CONTRACT_RETURN_RECORD_CREATED("#operator#添加了一条回款记录#source#");

    private String contentTemplate;

    public String getContentTemplate() {
        return contentTemplate;
    }

    ContractNewsType(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

}
