package com.zifangdt.ch.base.dto.customer.setting;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "cfg_number")
public class CfgNumber extends AuditEntity<Long> {

    private Integer number;

    private ChoiceConfigEnum name;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public ChoiceConfigEnum getName() {
        return name;
    }

    public void setName(ChoiceConfigEnum name) {
        this.name = name;
    }
}