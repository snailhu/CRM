package com.zifangdt.ch.base.enums.setting;

/**
 * 本枚举用于列举各种合同权限
 */
public enum UserConfigEnum {
    // contract
    NotifyContractUser("合同消息通知人"),
    RefundRecordPerm("回款记录权限"),
    RefundMessageSend("回款消息发送"),
    ContractDisablePerm("设置合同失效权限"),

    // customer
    CustomerDisablePerm("设置客户失效权限"),
    PoolCustomerDisablePerm("公海客户失效权限"),
    CustomerExportPerm("客户导出权限"),

    // project
    ProjectClosePerm("设置项目关闭权限"),
    ProjectScheduleNotify("设置项目排期通知"),
    ProjectMessageNotify("设置项目消息通知"),

    // product
    StockAlertPush("库存预警推送的用户");

    private String codename;

    UserConfigEnum(String codename){
        this.codename = codename;
    }

    public String getCodename() {
        return codename;
    }
}
