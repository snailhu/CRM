package com.zifangdt.ch.base.enums.setting;

import java.util.Arrays;
import java.util.List;

/**
 * 选项配置
 */
public enum ChoiceConfigEnum {
    // uaa 配置
    LoginAccount("登录账号设置", ChoiceEnum.UsernameLogin, ChoiceEnum.PhoneLogin, ChoiceEnum.HybridLogin),
    Password("密码生成策略", ChoiceEnum.FixPassword, ChoiceEnum.RandPassword),
    Level("开启等级设置", ChoiceEnum.getBoolChoice()),
    Tag("开启标签", ChoiceEnum.getBoolChoice()),

    // customer 配置
    CellAddressBool("地址小区", ChoiceEnum.getBoolChoice()),
    InfoValidate("客户信息校验", ChoiceEnum.getBoolChoice()),
    NameValidate("客户名称校验选择", ChoiceEnum.getBoolChoice()),
    MainContactValidate("主联系人校验", ChoiceEnum.getBoolChoice()),
    Type("客户类型", ChoiceEnum.getBoolChoice()),
    Source("客户来源", ChoiceEnum.getBoolChoice()),
    OwnerDisable("客户数据拥有者设置客户失效", ChoiceEnum.getBoolChoice()),
    Pool("公海客户开启", ChoiceEnum.getBoolChoice()),
    GetMax("每人领取公海客户上限", ChoiceEnum.getBoolChoice()),
    ReturnNoUpdate("多少天未对客户进行更新，回归公海", ChoiceEnum.getBoolChoice()),
    ReturnNoContract("多少天未形成合同，回归公海" , ChoiceEnum.getBoolChoice()),
    PoolCustomerDisable("客户数据拥有者对公海客户失效权限", ChoiceEnum.getBoolChoice()),
    CustomerUpperBound("客户上限", ChoiceEnum.getBoolChoice()),

    // Project 配置
    ProjectClosePermOwner("项目负责人有项目关闭权限", ChoiceEnum.getBoolChoice()),
    MaterialIn("材料进场配置", ChoiceEnum.getBoolChoice()),
    ProjectMessageNotifyOwner("项目消息通知项目负责人", ChoiceEnum.getBoolChoice()),
    ContractScheduleConfig("合同工期配置", ChoiceEnum.getBoolChoice()),
    NewItemAuditConfig("设置新增项审批设置", ChoiceEnum.getBoolChoice()),
    ProjectConfig("项目设置", ChoiceEnum.getBoolChoice()),
    ProjectGenerateTiming("项目生成条件", ChoiceEnum.ContractPassPermit, ChoiceEnum.ContractCreate),
    InitProjectStep("发起施工流程", ChoiceEnum.getBoolChoice()),
    ProjectStep("项目流程审批设置", ChoiceEnum.getBoolChoice());


    private String codename;
    private List<ChoiceEnum> availableChoice;

    ChoiceConfigEnum(String codename, ChoiceEnum... choiceEnums ){
        this.codename = codename;
        this.availableChoice = Arrays.asList(choiceEnums);
    }

    public String getCodename() {
        return codename;
    }

    public List<ChoiceEnum> getAvailableChoice() {
        return availableChoice;
    }
}
