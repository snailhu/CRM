package com.zifangdt.ch.market.bo;


import com.google.common.base.CaseFormat;
import com.zifangdt.ch.base.dto.BaseQueryBo;
import com.zifangdt.ch.base.enums.PromotionJoinStatusEnum;

public class PromotionJoinQueryBo extends BaseQueryBo {
    private String search; // 搜活动名称、采集信息、所有者名称
    private PromotionJoinStatusEnum status;
    private String ownerName;
    private Long ownerId;
    private OrderBy orderBy = OrderBy.createTime;
    private Sort sort = Sort.asc;

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public PromotionJoinStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PromotionJoinStatusEnum status) {
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public enum Sort {
        asc, desc;
    }

    public enum OrderBy{
        id, name, createTime, modifyTime;

        public String getColumnName() {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name());
        }
    }
}
