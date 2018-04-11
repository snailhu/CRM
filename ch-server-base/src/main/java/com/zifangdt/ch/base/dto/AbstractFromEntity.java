package com.zifangdt.ch.base.dto;

import org.springframework.beans.BeanUtils;

public abstract class AbstractFromEntity<S extends AuditEntity<Long>> {
    public AbstractFromEntity(S source) {
        BeanUtils.copyProperties(source, this);
    }
    public AbstractFromEntity(){}
}
