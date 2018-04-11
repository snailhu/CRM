package com.zifangdt.ch.base.dto;

public interface FromEntity<T, K> {
    K convertFrom(T entity);
}
