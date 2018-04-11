package com.zifangdt.ch.base.dto.projectmanager;

import java.util.Date;

public class Material {
    private Long id;

    private String materialTypeName;

    private Long productId;

    private Date materialInTime;

    private Long projectId;

    private Date constructTime;

    private Integer status;
    private Integer materialConstructStatus;

    private Long billId;

    private Integer isNew;

    private Date startTime;
    private Date endTime;

    public Integer getMaterialConstructStatus() {
        return materialConstructStatus;
    }

    public void setMaterialConstructStatus(Integer materialConstructStatus) {
        this.materialConstructStatus = materialConstructStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialTypeName() {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName) {
        this.materialTypeName = materialTypeName == null ? null : materialTypeName.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getMaterialInTime() {
        return materialInTime;
    }

    public void setMaterialInTime(Date materialInTime) {
        this.materialInTime = materialInTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getConstructTime() {
        return constructTime;
    }

    public void setConstructTime(Date constructTime) {
        this.constructTime = constructTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}