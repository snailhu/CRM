package com.zifangdt.ch.base.enums.ticket;

/**
 * 工单操作
 */
public enum TicketActionType implements DisplayableEnum{
    CREATE("创建", "%s 创建了工单"),
    EDIT("编辑", "%s 编辑了工单"),
    DELETE("删除", "%s 删除了工单"),
    ASSIGN("指派", "%s 指派工单给%s"),
    ACCEPT("接受", "%s 接受了工单"),
    REJECT("拒绝", "%s 拒绝工单"),
    TRANSFER("转派", "%s 转移了工单给%s"),
    START("开始", "%s 开始工单"),
    CREATE_RECEIPT("完成", "%s 完成工单，创建回执"),
    REJECT_RECEIPT("拒绝回执", "%s 拒绝回执"),
    CONFIRM_RECEIPT("确认回执", "%s 确认回执"),
    CREATE_RETURN_VISIT("回访客户", "%s 创建回访"),
    CREATE_CLEARING("添加工作量信息", "%s 创建结算"),
    CONFIRM_CLEARING("确认结算", "%s 结算确认"),
    FINISH("确认完结", "%s 确认工单完结"),
    REMARK("添加备注", "%s 添加了备注"),
    ;

    private final String contentTemplate;
    private final String name;
    TicketActionType(String name, String template) {
        this.contentTemplate = template;
        this.name = name;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public String getName() {
        return name;
    }
}
