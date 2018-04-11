package com.zifangdt.ch.base.enums.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.zifangdt.ch.base.enums.pair.ProcedureNode;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;

import static com.fasterxml.jackson.core.JsonParser.*;

public enum RefundNoticeStep {

    UnitAssign("指派工单时"),

    Delivery("发货时"),

    SchemeCheckOver("方案审批结束");

    private final String name;

    RefundNoticeStep(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }


    public static RefundNoticeStep from(ProcedureNode procedureNode) {
        switch (procedureNode) {
            case APPROVING:
                return RefundNoticeStep.SchemeCheckOver;
            case ASSIGNING:
                return RefundNoticeStep.UnitAssign;
            case DELIVERING:
                return RefundNoticeStep.Delivery;
        }
        return null;
    }
}