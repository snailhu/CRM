package com.zifangdt.ch.market.bo;

import com.google.common.base.CaseFormat;
import com.zifangdt.ch.base.dto.BaseQueryBo;
import com.zifangdt.ch.base.enums.PromotionStatusEnum;

import com.zifangdt.ch.base.enums.PromotionTypeEnum;

public class PromotionQueryBo extends BaseQueryBo {
    private PromotionTypeEnum type;
    private PromotionStatusEnum status;
    private OrderBy orderBy = OrderBy.createTime;

    public PromotionTypeEnum getType() {
        return type;
    }

    public void setType(PromotionTypeEnum type) {
        this.type = type;
    }

    public PromotionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PromotionStatusEnum status) {
        this.status = status;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public enum OrderBy{
        id, name, createTime, modifyTime;

        public String getColumnName() {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name());
        }
    }
}