package com.zifangdt.ch.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/1/20.
 */
public enum RedirectType {
    PROCESS("processId"),
    TRACK("trackId"),
    CONTRACT_DETAIL("contractId"),
    TODO_TASK_DETAIL("todoTaskId"),
    SALES_CLUE_DETAIL("salesClueId"),
    DAILY_DETAIL("dailyId"),
    JOURNAL_DETAIL("journalId"),
    CUSTOMER_DETAIL("customerId"),
    CUSTOMER_ACTION_DETAIL("actionId"),
    Purchase("purchaseId"),
    Project("projectId"),
    ProjectWareHouseDeliver("projectId,UnitId"),
    AssignProjectUnit("projectId"),
    ProjectTicket("projectId"),
    Project_Unit_Start("projectId"),
    PromotionAffiliate("promotionAffiliateId"), //活动推广id
    PromotionJoin("promotionJoinId"),
    Product("productId"),
    Ticket("ticketId");

    private String argumentList;

    RedirectType(String argumentList) {
        this.argumentList = argumentList;
    }

    public Map<String, Object> parse(Object[] args) {
        String[] names = this.argumentList.split(",");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    public String getArgumentList() {
        return argumentList;
    }
}
