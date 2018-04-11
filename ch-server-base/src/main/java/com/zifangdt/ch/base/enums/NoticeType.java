package com.zifangdt.ch.base.enums;

/**
 * Created by 袁兵 on 2017/10/18.
 */
public enum NoticeType {
    CUSTOMER_RETURNED("客户%s已被退还至公海，点击查看"),
    CUSTOMER_TRANSFERRED("%s转移客户%s给您，点击查看"),

    DAILY_SUBMITTED("%s提交了日报，点击查看"),
    DAILY_COMMENTED("%s回复了你的日报，点击查看"),
    DAILY_COMMENT_COMMENTED("%s回复了你的回复，点击查看"),
    DAILY_LIKED("%s点赞了你的日报，点击查看"),
    DAILY_COMMENT_LIKED("%s点赞了你的回复，点击查看"),

    PROCESS_RECEIVED("收到[%s(%s)]，请尽快处理"),
    PROCESS_STARTED_CC("[%s]创建了流程[%s]，点击查看"),
    PROCESS_AGREED("[%s]通过了[%s]，通过意见是“%s”，点击查看"),
    PROCESS_REJECTED("[%s]退回了[%s]，退回原因是“%s”，点击查看"),
    PROCESS_CANCELED("[%s]取消了流程[%s]，点击查看"),

    BILL_FEED_BACKED("收到客户反馈%s，点击查看"),

    CONTRACT_TRANSFERRED("%s转移合同%s给您，点击查看"),
    CONTRACT_REFUND_RECORD("合同%s收到一笔回款，点击查看"),
    CONTRACT_REFUND_MESSAGE("%s发来合同%s的回款信息，点击处理"),

    PROJECT_PROCESS_STARTED("收到%s项目,排期完成，点击查看"),
    PROJECT_PROCESS_PLAN("%s项目已创建,尽快排期，点击查看"),
    PROJECT_TASK_STARTED("您收到一个[%s]的任务，点击查看"),
    PROJECT_SUB_TASK_STARTED("您收到一个[%s]的任务，点击查看"),
    PROJECT_MATERIAL_STARTED("您收到一个[%s]的材料审批，点击查看"),
    PROJECT_MATERIAL_PROCESS_STARTED("收到%s项目,%s工期材料，点击查看"),
    PROJECT_MATERIAL_PROCESS_REJECTED("%s项目的,%s材料,已经被退回，点击查看"),

    TODO_CREATED("%s发来待办任务%s，点击处理"),
    TODO_COMPLETED("%s完成待办任务%s，点击查看"),
    TODO_DELETED("%s取消待办任务%s，点击查看"),
    TODO_TIMEOUT("待办任务%s已超过截止日期，点击查看"),

    SUB_CREATED("%s发来子任务%s，点击处理"),
    SUB_COMPLETED("%s完成子任务%s，点击查看"),
    SUB_DELETED("%s取消子任务%s，点击查看"),
    SUB_TIMEOUT("子任务%s已超过截止日期，点击查看"),

    ACTIVITY_REFRESHED("活动%s新增加%s条报名数据，点击查看"),
    ACTIVITY_PUBLISHED("%s发布活动%s，点击查看"),

    CLUE_CREATED("%s发布线索%s，点击查看"),
    CLUE_INVALID("%s将线索%s设为无效，点击查看"),

    PURCHASE_AUDIT("收到采购[%s]审核，请尽快处理"),
    STOCK_LOW_ALERT("产品[%s]库存低于预警值，请尽快处理"),

    REVENUE_ADVANCE("收到一条待回款消息:[%s]预付款，点击查看"),
    REVENUE_FULL("收到一条待回款消息:[%s]一次性结清，点击查看"),

    PROJECT_CLEAR_ACCOUNTS("%s 项目盘点，点击处理"),
    START_PROJECT("%s 项目开始，点击查看"),
    START_PROJECT_SCHEME("%s 安装方案开始！"),
    START_PROJECT_UNIT("%s-%s-%s 开始，点击查看"),
    OBTAIN_PROJECT("%s 领取项目%s，点击查看"),
    ASSIGN_CONSTRUCTION_UNIT("%s中%s指派工单，点击查看"),
    NOTICE_WAREHOUSE_DELIVERY("%s中%s,%s环节，提醒发货"),
    WAREHOUSE_DELIVERY("%s中%s,%s材料发货，请尽快处理"),
    CONSTRUCTION_UNIT_REFUND("项目进行至%s-%s-%s,请确认%s待回款(如已到账，请忽略)"),
    CONSTRUCTION_CLEAR_ACCOUNTS("%s中%s工期结算，点击处理"),


    TICKET_CREATE("%s，创建工单%s，点击查看"),
    TICKET_ASSIGN("%s，指派工单%s"),
    TICKET_ACCEPT("%s，接受工单%s"),
    TICKET_REJECT("收到%s，拒绝工单%s"),
    TICKET_DELETE("收到%s，删除工单%s"),
    TICKET_START("收到%s，开始工单%s"),
    TICKET_CREATE_RECEIPT("收到%s，提交回执工单%s"),
    TICKET_CONFIRM_RECEIPT("%s，确认回执工单%s"),
    TICKET_REJECT_RECEIPT("%s，拒绝回执工单%s"),
    TICKET_RETURN_VISIT("收到%s，回访工单%s"),
    TICKET_CREATE_CLEARING("收到%s，结算工单%s"),
    TICKET_CLEARING_CONFIRM("收到%s，结算到账工单%s"),
    TICKET_FINISH("收到%s，完结工单%s"),
    TICKET_NEW_REMARK("收到%s，备注工单%s")
    ;

    private String contentTemplate;

    NoticeType(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public static NoticeType from(ApproveBehavior approveBehavior) {
        if (approveBehavior == ApproveBehavior.RESOLVE) {
            return NoticeType.PROCESS_AGREED;
        } else if (approveBehavior == ApproveBehavior.REJECT) {
            return NoticeType.PROCESS_REJECTED;
        } else {
            throw new RuntimeException();
        }
    }
}
