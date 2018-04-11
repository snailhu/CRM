package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.bo.CustomerAnalysisUpdateBo;
import com.zifangdt.ch.base.enums.ApproveBehavior;
import com.zifangdt.ch.base.util.DateUtil;

/**
 * Created by 袁兵 on 2018/1/23.
 */
public enum NewsType {

    CONTRACT_CREATED("?创建了合同?"),
    CONTRACT_EDITED("?编辑了合同?"),
    CONTRACT_INVALID("?失效了合同?"),
    CONTRACT_TRANSFERRED("?转移了合同?给?"),

    SCHEDULE_CREATED("?创建了日程?"),
    SCHEDULE_DELETED("?删除了日程?"),
    TODO_CREATED("?创建了待办?"),
    TODO_COMPLETED("?完成了待办?"),
    TODO_DELETED("?删除了待办?"),

    PROJECT_CREATED("项目?创建成功"),
    START_PROJECT("?开始项目"),
    TOKEN_PROJECT("?领取项目"),
    START_PROJECT_CONSTRUCTION("?开始工期?"),
    NOTICE_REPERTORY("?提醒仓库发货"),
    DELETE_PROJECT("?删除了项目"),
    PAY_OVER_PROJECT("?交付了项目"),
    FINANCE_OVER_PROJECT("?完成项目结算"),
    FINANCE_INVENTORY_PROJECT("?完成?项目结算"),


    PROCESS_RESOLVED("?审批通过了?，通过意见是?"),
    PROCESS_REJECTED("?审批退回了?，退回意见是?"),

    CUSTOMER_CREATED("?创建了客户?"),
    CUSTOMER_TAKEN("?从公海领取了客户?"),
    CUSTOMER_RETURNED("客户?由于长时间未被维护，已归为公海客户"),
    CUSTOMER_BASIC_EDITED("?编辑了客户?的基本信息"),
    CUSTOMER_WORTH_EDITED("?编辑了客户?的价值为?"),
    CUSTOMER_ORDER_SIZE_EDITED("?编辑了客户?的订单大小为?"),
    CUSTOMER_SIGN_DATE_EDITED("?编辑了客户?的预估签约日期为?"),
    CUSTOMER_MONEY_EDITED("?编辑了客户?的预估金额为?"),
    CUSTOMER_TRANSFERRED("?转移了客户?给?"),
    CUSTOMER_VISITED("?拜访了客户?"),
    CUSTOMER_CARED("?关怀了客户?"),
    CUSTOMER_CALLED("?电联了客户?"),
    CUSTOMER_STAGE_EDITED("?编辑了客户?的所属阶段为?"),
    CUSTOMER_INVALID("?失效了客户?"),

    BILL_CREATED("?创建了报价单?"),

    CLUE_CREATED("?创建了销售线索?"),
    CLUE_BASIC_INFO_MODIFIED("?修改了销售线索?的基本资料"),
    CLUE_DELETED("?删除了销售线索?"),
    CLUE_INVALID("?将销售线索?设为了无效信息"),
//    CLUE_SCHEDULE_CREATED("?创建了日程?"),
//    CLUE_SCHEDULE_DELETED("?删除了日程?"),
//    CLUE_TODO_TASK_CREATED("?创建了待办任务?"),
//    CLUE_TODO_TASK_COMPLETED("?完成了待办任务?"),
//    CLUE_TODO_TASK_DELETED("?删除了待办任务?"),
//    CLUE_CUSTOMER_CREATED("?从销售线索?中创建了客户?"),
//    CLUE_CUSTOMER_VISITED("?拜访了客户?"),
//    CLUE_CUSTOMER_CALLED("?电联了客户?"),
//    CLUE_CUSTOMER_CARED("?关怀了客户?"),
    CLUE_READ("?已查看销售线索?"),
    CLUE_OPERATED("?已处理销售线索?"),

    PRODUCT_CREATE("?创建了产品"),
    PRODUCT_EDIT("?修改了产品"),
    ;

    private String contentTemplate;

    public String getContentTemplate() {
        return contentTemplate;
    }

    NewsType(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public static NewsType from(ApproveBehavior approveBehavior) {
        if (approveBehavior == ApproveBehavior.RESOLVE) {
            return NewsType.PROCESS_RESOLVED;
        } else if (approveBehavior == ApproveBehavior.REJECT) {
            return NewsType.PROCESS_REJECTED;
        } else {
            throw new RuntimeException();
        }
    }

    public static Object[] from(CustomerAnalysisUpdateBo bo) {
        if (bo.getWorth() != null) {
            return new Object[]{NewsType.CUSTOMER_WORTH_EDITED, bo.getWorth().name};
        } else if (bo.getOrderSize() != null) {
            return new Object[]{NewsType.CUSTOMER_ORDER_SIZE_EDITED, bo.getOrderSize().name};
        } else if (bo.getPredictedSignTime() != null) {
            return new Object[]{NewsType.CUSTOMER_SIGN_DATE_EDITED, DateUtil.format(bo.getPredictedSignTime(), "yyyy-MM-dd")};
        } else if (bo.getPredictedMoney() != null) {
            return new Object[]{NewsType.CUSTOMER_MONEY_EDITED, bo.getPredictedMoney()};
        } else {
            throw new RuntimeException();
        }
    }

    public static NewsType from(ActionType actionType) {
        if (actionType == ActionType.VISIT) {
            return NewsType.CUSTOMER_VISITED;
        } else if (actionType == ActionType.CALL) {
            return NewsType.CUSTOMER_CALLED;
        } else if (actionType == ActionType.CARE) {
            return NewsType.CUSTOMER_CARED;
        } else {
            throw new RuntimeException();
        }
    }
}
