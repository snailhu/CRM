package com.zifangdt.ch.base.dto.syssetting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.setting.StepEnum;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "cfg_step_definition")
@JsonIgnoreProperties(value = "priority", allowGetters = true)
public class StepDefinition extends AuditEntity<Long> implements Serializable {

    private String name;

    private Long operator;

    private Integer sort;

    private StepEnum stepEnum;

    @Transient
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StepEnum getStepEnum() {
        return stepEnum;
    }

    public void setStepEnum(StepEnum stepEnum) {
        this.stepEnum = stepEnum;
    }

    public Long getPriority(){
        return getId() <= 3 ? 1l: 2l;
    }
}