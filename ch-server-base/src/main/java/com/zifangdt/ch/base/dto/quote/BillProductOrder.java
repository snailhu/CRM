package com.zifangdt.ch.base.dto.quote;

import java.math.BigDecimal;

public class BillProductOrder {
    private Long id;

    private Integer totalNum;

    private BigDecimal totalPrice;

    private String appendixUrl;

    private Long schemeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAppendixUrl() {
        return appendixUrl;
    }

    public void setAppendixUrl(String appendixUrl) {
        this.appendixUrl = appendixUrl == null ? null : appendixUrl.trim();
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }
}