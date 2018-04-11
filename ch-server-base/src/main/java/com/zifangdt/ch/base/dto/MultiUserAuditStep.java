package com.zifangdt.ch.base.dto;

import com.zifangdt.ch.base.dto.setting.SimpleUser;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.setting.StepEnum;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "multi_user_audit_step")
public class MultiUserAuditStep extends AuditEntity<Long> {

    private String name;

    private List<Long> operators;

    private Integer sort;

    private StepEnum stepEnum;

    @Transient
    private List<SimpleUser> operatorUser;

    public String getName() {
        return name;
    }

    public MultiUserAuditStep setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getOperators() {
        return operators;
    }

    public MultiUserAuditStep setOperators(List<Long> operators) {
        this.operators = operators;
        return this;
    }

    public StepEnum getStepEnum() {
        return stepEnum;
    }

    public MultiUserAuditStep setStepEnum(StepEnum stepEnum) {
        this.stepEnum = stepEnum;
        return this;
    }

    public List<SimpleUser> getOperatorUser() {
        return operatorUser;
    }

    public MultiUserAuditStep setOperatorUser(List<SimpleUser> operatorUser) {
        this.operatorUser = operatorUser;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public MultiUserAuditStep setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}