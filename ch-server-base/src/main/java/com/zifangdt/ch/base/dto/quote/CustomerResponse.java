package com.zifangdt.ch.base.dto.quote;

import java.util.Date;

public class CustomerResponse {
    private Long id;

    private Long customerId;

    private Long sellerId;

    private String seller;

    private String designer;

    private Long designerId;

    private Integer score;

    private String responseDes;

    private String responseImgrul;

    private String responser;

    private Integer responserTel;

    private String schemeName;

    private Long schemeId;

    private Integer responseResult;

    private Long billId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer == null ? null : designer.trim();
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResponseDes() {
        return responseDes;
    }

    public void setResponseDes(String responseDes) {
        this.responseDes = responseDes == null ? null : responseDes.trim();
    }

    public String getResponseImgrul() {
        return responseImgrul;
    }

    public void setResponseImgrul(String responseImgrul) {
        this.responseImgrul = responseImgrul == null ? null : responseImgrul.trim();
    }

    public String getResponser() {
        return responser;
    }

    public void setResponser(String responser) {
        this.responser = responser == null ? null : responser.trim();
    }

    public Integer getResponserTel() {
        return responserTel;
    }

    public void setResponserTel(Integer responserTel) {
        this.responserTel = responserTel;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName == null ? null : schemeName.trim();
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(Integer responseResult) {
        this.responseResult = responseResult;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}