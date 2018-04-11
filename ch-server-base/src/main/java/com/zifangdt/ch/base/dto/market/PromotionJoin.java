package com.zifangdt.ch.base.dto.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zifangdt.ch.base.converter.NameGenerator;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.PromotionJoinStatusEnum;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Table(name = "prom_join")
@JsonIgnoreProperties(value = {"name", "phone"}, allowGetters = true)
public class PromotionJoin extends AuditEntity<Long> {

    static final ObjectMapper mapper = new ObjectMapper();

    private PromotionJoinStatusEnum status;

    @NameGenerator
    private Long ownerId;

    private Long promotionId;

    private String submitInfo;

    private Long relateId;

    @Transient
    private String promotionName;

    @Transient
    private Long promotionAffiliateId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getSubmitInfo() {
        return submitInfo;
    }

    public void setSubmitInfo(String submitInfo) {
        this.submitInfo = submitInfo == null ? null : submitInfo.trim();
    }

    public PromotionJoinStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PromotionJoinStatusEnum status) {
        this.status = status;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getName() throws IOException {
        if (getSubmitInfo() != null){
            Map<String, String> map = mapper.readValue(getSubmitInfo(), Map.class);
            return map.getOrDefault("name", null);
        } else {
            return null;
        }
    }

    public String getPhone() throws IOException {
        if (getSubmitInfo() != null){
            Map<String, String> map = mapper.readValue(getSubmitInfo(), Map.class);
            return map.getOrDefault("phone", null);
        } else {
            return null;
        }
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public Long getPromotionAffiliateId() {
        return promotionAffiliateId;
    }

    public PromotionJoin setPromotionAffiliateId(Long promotionAffiliateId) {
        this.promotionAffiliateId = promotionAffiliateId;
        return this;
    }
}