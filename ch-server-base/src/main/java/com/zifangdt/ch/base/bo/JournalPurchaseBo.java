package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.dto.product.entity.Purchase;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/4/4.
 */
public class JournalPurchaseBo {
    private Purchase purchase;
    private Date plannedDate;
    private BigDecimal plannedMoney;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public BigDecimal getPlannedMoney() {
        return plannedMoney;
    }

    public void setPlannedMoney(BigDecimal plannedMoney) {
        this.plannedMoney = plannedMoney;
    }
}
