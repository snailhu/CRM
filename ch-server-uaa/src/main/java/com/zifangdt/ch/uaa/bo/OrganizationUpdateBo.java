package com.zifangdt.ch.uaa.bo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class OrganizationUpdateBo {
    @NotBlank
    private String name;
    private Integer priority;
    private String description;
    private List<Long> roles;

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
