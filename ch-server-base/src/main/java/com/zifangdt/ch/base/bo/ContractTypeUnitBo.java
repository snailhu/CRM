package com.zifangdt.ch.base.bo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;

import java.util.List;

public class ContractTypeUnitBo {

    //工序名称
    private String typeUnitName;
    //工序id
    private Long  typeUnitId;

    //回款环节
    private List<String> refundNoticeSteps;



    public String getTypeUnitName() {
        return typeUnitName;
    }

    public void setTypeUnitName(String typeUnitName) {
        this.typeUnitName = typeUnitName;
    }

    public Long getTypeUnitId() {
        return typeUnitId;
    }

    public void setTypeUnitId(Long typeUnitId) {
        this.typeUnitId = typeUnitId;
    }

    public List<String> getRefundNoticeSteps() {
        return refundNoticeSteps;
    }

    public void setRefundNoticeSteps(List<String> refundNoticeSteps) {
        this.refundNoticeSteps = refundNoticeSteps;
    }
}