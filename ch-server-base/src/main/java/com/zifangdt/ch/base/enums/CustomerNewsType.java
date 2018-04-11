package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/9/20.
 */
public enum CustomerNewsType implements NewsType {
    CUSTOMER_CREATED("#operator#创建了客户#customer#"),
    CUSTOMER_RECEIVED("#operator#从公海领取了客户#customer#"),
    CUSTOMER_RETURNED("客户#customer#由于长时间未被维护，已归为公海客户"),
    CUSTOMER_BASIC_INFO_MODIFIED("#operator#修改了客户#customer#的基本资料"),
    CUSTOMER_STAT_INFO_MODIFIED("#operator#修改了客户#customer#的分析信息"),
    CUSTOMER_VISITED("#operator#拜访了客户#customer#"),
    CUSTOMER_CALLED("#operator#电联了客户#customer#"),
    CUSTOMER_CARED("#operator#关怀了客户#customer#"),
    CUSTOMER_STAGE_MODIFIED("#operator#修改了客户#customer#的阶段"),
    CUSTOMER_CREATED_FROM_CLUE("#operator#将销售线索#source#转为客户"),

    CUSTOMER_SCHEDULE_CREATED("#operator#创建了日程#source#"),
    CUSTOMER_TODO_TASK_CREATED("#operator#创建了待办任务#source#"),
    CUSTOMER_TODO_TASK_COMPLETED("#operator#完成了待办任务#source#"),
    CUSTOMER_CONTRACT_CREATED("#operator#创建了合同#source#"),
    CUSTOMER_FLOW_COMPLETED("#operator#完成了流程任务#source#"),
    CUSTOMER_FLOW_BACK("#operator#退回了流程任务#source#"),
    CUSTOMER_FLOW_CREATED_SUB_TASK("#operator#创建了子任务#source#"),
    CUSTOMER_FLOW_CHECKED("工程部审批通过#source#"),
    CUSTOMER_FLOW_TRANSFORM("#operator#转移了流程任务#source#"),
    CUSTOMER_FLOW_AGREE_CONSTRUCT("#operator#通过了工期#source#"),
    CUSTOMER_FLOW_AGREE("#operator#验收了流程任务名称#source#"),
    CUSTOMER_FLOW_CREATED("#operator#新增了工期#source#材料"),
    CUSTOMER_FLOW_START_PROJECT("#operator#开始了项目#source#"),
    CUSTOMER_FLOW_START("#operator#发起了#source#施工流程");

    private String contentTemplate;

    public String getContentTemplate() {
        return contentTemplate;
    }

    CustomerNewsType(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public static CustomerNewsType getFromActionType(Long actionType) {
        String type = DataEnum.customerActionTypes.getValue(actionType);
        return type.equals("拜访") ? CustomerNewsType.CUSTOMER_VISITED :
                type.equals("电联") ? CustomerNewsType.CUSTOMER_CALLED :
                        type.equals("关怀") ? CustomerNewsType.CUSTOMER_CARED : null;
    }
}
