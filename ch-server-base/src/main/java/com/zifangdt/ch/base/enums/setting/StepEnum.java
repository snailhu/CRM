package com.zifangdt.ch.base.enums.setting;

/**
 * 步骤类型
 */
public enum StepEnum {
    // project
    MaterialImportAudit("材料进场审批步骤"),
    NewMaterialImportAudit("新增材料进场配置"),
    ProjectSetupAudit("工期安装方案审批"),
    PriceSheetAudit("报价单审批"),

    // product
    PurchaseAudit("采购审批");

    private final String description;

    StepEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}