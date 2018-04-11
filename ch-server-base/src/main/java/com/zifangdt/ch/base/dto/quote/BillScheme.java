package com.zifangdt.ch.base.dto.quote;

import java.util.Date;

public class  BillScheme {
    private Long id;

    private Long quoteBillId;

    private String schemeName;

    private String designImgurl;

    private String description;

    private Long designerId;

    private String designerName;

    private Date createTime;

    private String remark;

    private Long productOrderId;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuoteBillId() {
        return quoteBillId;
    }

    public void setQuoteBillId(Long quoteBillId) {
        this.quoteBillId = quoteBillId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName == null ? null : schemeName.trim();
    }

    public String getDesignImgurl() {
        return designImgurl;
    }

    public void setDesignImgurl(String designImgurl) {
        this.designImgurl = designImgurl == null ? null : designImgurl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName == null ? null : designerName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}