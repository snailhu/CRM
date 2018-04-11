package com.zifangdt.ch.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.enums.setting.ChoiceConfigEnum;
import com.zifangdt.ch.base.enums.setting.ChoiceEnum;

import java.util.List;

/**
 * 多选1配置 基类
 * 为了保证性能，各个微服务的配置需要存储在本地数据库，如果需要使用多选1配置，
 * 可以继承这个类
 */
@JsonIgnoreProperties(value={ "choices" }, allowGetters=true)
public abstract class BaseChoiceConfig<T> extends AuditEntity<T> {
    private ChoiceConfigEnum config;

    private String description;

    private ChoiceEnum choice;

    public ChoiceConfigEnum  getConfig() {
        return config;
    }

    public void setConfig(ChoiceConfigEnum config) {
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public ChoiceEnum getChoice() {
        return choice;
    }

    public void setChoice(ChoiceEnum choice) {
        this.choice = choice;
    }

    public List<ChoiceEnum> getChoices(){
        return config.getAvailableChoice();
    }

    public BaseChoiceConfig() {}
}