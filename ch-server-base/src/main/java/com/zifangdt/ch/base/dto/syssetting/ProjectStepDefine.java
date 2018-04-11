package com.zifangdt.ch.base.dto.syssetting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "project_step_definition")
@JsonIgnoreProperties(value = "userId", allowGetters = true)
public class ProjectStepDefine extends AuditEntity<Long> {

    private String name;

    private Long operator;

    private Integer sort;

    private Long type; // 施工类型

    private Long priority;

    private String description;

    @Transient
    private String username;

    @Transient
    private String priorityName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getType() {
        return type;
    }

    public ProjectStepDefine setType(Long type) {
        this.type = type;
        return this;
    }

    public Long getPriority() {
        return priority;
    }

    public ProjectStepDefine setPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStepDefine setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public ProjectStepDefine setPriorityName(String priorityName) {
        this.priorityName = priorityName;
        return this;
    }

    public Long getUserId() {
        return operator;
    }
}
