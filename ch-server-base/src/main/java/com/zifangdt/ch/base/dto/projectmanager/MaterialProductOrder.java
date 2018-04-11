package com.zifangdt.ch.base.dto.projectmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.common.File;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@JsonIgnoreProperties(value = "id", allowGetters = true)
public class    MaterialProductOrder {
    private Long id;

    private Integer totalNum;

    private BigDecimal totalPrice;

    @JsonIgnore
    private String appendixUrl;

    private Long materialId;

    private Date createTime;

    private String creator;

    private String description;

    private int isNew;

    private int status;
    private String[] appendixUrls;

    private List<File> appendUrlsDetail;

    public List<File> getAppendUrlsDetail() {
        return appendUrlsDetail;
    }

    public void setAppendUrlsDetail(List<File> appendUrlsDetail) {
        this.appendUrlsDetail = appendUrlsDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAppendixUrls() {
        return appendixUrls;
    }

    public void setAppendixUrls(String[] appendixUrls) {
        this.appendixUrls = appendixUrls;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private List<MaterialProduct> materialProductList;

    public List<MaterialProduct> getMaterialProductList() {
        return materialProductList;
    }

    public void setMaterialProductList(List<MaterialProduct> materialProductList) {
        this.materialProductList = materialProductList;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }


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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = description == null ? null : description.trim();
    }
}