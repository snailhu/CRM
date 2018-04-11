package com.zifangdt.ch.base.bo.setting;

import com.zifangdt.ch.base.validation.UserExist;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 步骤，步骤执行人是一个特定用户
 */
public class StepUser {

    @NotEmpty
    private String name;

    @NotNull
    @UserExist
    private Long userId;

    private Long priority;

    private String description;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriority() {
        return priority;
    }

    public StepUser setPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StepUser setDescription(String description) {
        this.description = description;
        return this;
    }
}
