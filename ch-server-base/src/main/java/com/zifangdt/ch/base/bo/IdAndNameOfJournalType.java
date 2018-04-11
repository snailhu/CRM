package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public class IdAndNameOfJournalType extends IdAndName {
    private RevenueOrExpense belongsTo;

    public RevenueOrExpense getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(RevenueOrExpense belongsTo) {
        this.belongsTo = belongsTo;
    }
}
