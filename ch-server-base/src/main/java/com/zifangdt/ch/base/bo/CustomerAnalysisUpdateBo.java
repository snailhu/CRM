package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.pair.CustomerWorth;
import com.zifangdt.ch.base.enums.pair.OrderSize;
import com.zifangdt.ch.base.validation.PositiveNumber;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/1/25.
 */
public class CustomerAnalysisUpdateBo {
    private CustomerWorth worth;
    private OrderSize orderSize;
    private Date predictedSignTime;
    @PositiveNumber
    private BigDecimal predictedMoney;

    public CustomerWorth getWorth() {
        return worth;
    }

    public void setWorth(CustomerWorth worth) {
        this.worth = worth;
    }

    public OrderSize getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(OrderSize orderSize) {
        this.orderSize = orderSize;
    }

    public Date getPredictedSignTime() {
        return predictedSignTime;
    }

    public void setPredictedSignTime(Date predictedSignTime) {
        this.predictedSignTime = predictedSignTime;
    }

    public BigDecimal getPredictedMoney() {
        return predictedMoney;
    }

    public void setPredictedMoney(BigDecimal predictedMoney) {
        this.predictedMoney = predictedMoney;
    }
}
