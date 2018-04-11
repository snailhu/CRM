package com.zifangdt.ch.base.dto.market;

import com.zifangdt.ch.base.enums.PromotionStatusEnum;
import com.zifangdt.ch.base.enums.PromotionTypeEnum;

import java.util.Date;
import java.util.List;

public class PromotionBo {

    private Long id;
    private String name;
    private PromotionTypeEnum type;
    private PromotionStatusEnum status;
    private Date createTime;
    private Long pv;
    private Long joinNumber;
    private String publishTo;
    private List<PromotionAffiliate> affiliates;
    private List<Component> components;
    private List<PromotionJoin> joins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(Long joinNumber) {
        this.joinNumber = joinNumber;
    }

    public String getPublishTo() {
        return publishTo;
    }

    public void setPublishTo(String publishTo) {
        this.publishTo = publishTo;
    }

    public List<PromotionAffiliate> getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(List<PromotionAffiliate> affiliates) {
        this.affiliates = affiliates;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<PromotionJoin> getJoins() {
        return joins;
    }

    public void setJoins(List<PromotionJoin> joins) {
        this.joins = joins;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
