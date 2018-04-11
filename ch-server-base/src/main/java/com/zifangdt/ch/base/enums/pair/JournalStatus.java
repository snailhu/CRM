package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum JournalStatus implements PairedEnum {
    PENDING_REVENUE(1, "待回款", RevenueOrExpense.REVENUE),
    DONE_REVENUE(2, "已回款", RevenueOrExpense.REVENUE),
    PENDING_EXPENSE(3, "待支付", RevenueOrExpense.EXPENSE),
    DONE_EXPENSE(4, "已支付", RevenueOrExpense.EXPENSE),
    DELETED(5, "已删除", null),
    INVALID(6, "已失效", null);

    final int intVerifier;
    final String name;
    final RevenueOrExpense belongsTo;

    JournalStatus(int intVerifier, String name, RevenueOrExpense belongsTo) {
        this.intVerifier = intVerifier;
        this.name = name;
        this.belongsTo = belongsTo;
    }

    public RevenueOrExpense getBelongsTo() {
        return belongsTo;
    }
}
