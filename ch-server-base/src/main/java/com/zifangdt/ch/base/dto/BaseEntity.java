package com.zifangdt.ch.base.dto;

import javax.persistence.Id;

/**
 * Created by 袁兵 on 2017/8/29.
 */
public class BaseEntity<T> {
    @Id
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }


}
