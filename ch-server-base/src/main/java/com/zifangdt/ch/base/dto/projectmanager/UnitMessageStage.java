package com.zifangdt.ch.base.dto.projectmanager;

import com.zifangdt.ch.base.enums.project.RefundNoticeStep;

public class UnitMessageStage {
    private Long id;

    private RefundNoticeStep messageStage;

    private Long unitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefundNoticeStep getMessageStage() {
        return messageStage;
    }

    public void setMessageStage(RefundNoticeStep messageStage) {
        this.messageStage = messageStage;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}