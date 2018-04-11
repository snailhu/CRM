package com.zifangdt.ch.base.event;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.dto.BaseEntity;

import org.springframework.context.ApplicationEvent;

/**
 * 由于java类型擦除，不能直接用generic event
 * @param <T>
 */
public abstract class EntityCreateEvent<T extends BaseEntity> extends ApplicationEvent{
    private T entity;

    public EntityCreateEvent(Object source, T entity) {
        super(source);
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    public EntityCreateEvent<T> setEntity(T entity) {
        this.entity = entity;
        return this;
    }
}
