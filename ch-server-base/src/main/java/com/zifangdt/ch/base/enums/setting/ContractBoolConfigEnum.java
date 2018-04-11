package com.zifangdt.ch.base.enums.setting;

public enum ContractBoolConfigEnum {
    NotifyContractOwner("合同消息是否通知合同拥有者"),
    ContractApprove("合同审批是否开关"),
    DisableContractCloseProject("设置合同失效是否同时关闭项目"),
    DisableContractByOwner("合同拥有者是否能设置合同失效"),
    ContractType("是否开启合同类型");

    private String codename;
    private ContractBoolConfigEnum(String codename){
       this.codename = codename;
    }

    public String getCodename() {
        return codename;
    }
}
