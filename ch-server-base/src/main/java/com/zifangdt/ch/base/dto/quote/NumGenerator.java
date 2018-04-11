package com.zifangdt.ch.base.dto.quote;

import com.zifangdt.ch.base.dto.BaseEntity;

import java.io.Serializable;

public class NumGenerator implements Serializable {
    private Long id;

    private String stub;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStub() {
        return stub;
    }

    public void setStub(String stub) {
        this.stub = stub;
    }
}