package com.zifangdt.ch.base.bo.setting;

import com.zifangdt.ch.base.bo.PageQueryBo;

import java.util.List;

public class NewsPageQueryProjectBo extends PageQueryBo {

    private Long relatedId;
    private List<Long> unitIds;

    public List<Long> getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(List<Long> unitIds) {
        this.unitIds = unitIds;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public NewsPageQueryProjectBo(){
        super.setSize(20);
    }
}