package com.zifangdt.ch.base.bo.setting;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class StepMultiUser {
    @NotEmpty
    private String name;

    @NotEmpty
    private List<Long> userIds;

    public String getName() {
        return name;
    }

    public StepMultiUser setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public StepMultiUser setUserIds(List<Long> userIds) {
        this.userIds = userIds;
        return this;
    }
}
