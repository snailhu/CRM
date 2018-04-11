package com.zifangdt.ch.base.event;

import com.zifangdt.ch.base.dto.BaseEntity;

import org.springframework.context.ApplicationEvent;

public abstract class EntityUpdateEvent<T extends BaseEntity> extends ApplicationEvent {
    private T entity;
    public EntityUpdateEvent(Object source, T entity) {
        super(source);
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    public EntityUpdateEvent<T> setEntity(T entity) {
        this.entity = entity;
        return this;
    }
}
