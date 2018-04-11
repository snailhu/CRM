package com.zifangdt.ch.base.dto.market;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NameGenerator;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "promotion_affiliate")
public class PromotionAffiliate extends AuditEntity<Long> {

    @Value("${PROMOTION_URL}")
    @Transient
    private String promotionUrl;

    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long userId;

    private Long promotionId;

    @Transient
    private String promotionName;

    private Integer visitCnt;

    private Integer joinCnt;

    @Transient
    private List<PromotionJoin> joinList;

    @Transient
    private String url;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(Integer visitCnt) {
        this.visitCnt = visitCnt;
    }

    public Integer getJoinCnt() {
        return joinCnt;
    }

    public void setJoinCnt(Integer joinCnt) {
        this.joinCnt = joinCnt;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public List<PromotionJoin> getJoinList() {
        return joinList;
    }

    public void setJoinList(List<PromotionJoin> joinList) {
        this.joinList = joinList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void updateUrl(String baseUrl) {
        url = baseUrl + "?p=" + getPromotionId() + "&u=" + getUserId();
    }
}