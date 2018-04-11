package com.zifangdt.ch.market.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.enums.PromotionTypeEnum;
import com.zifangdt.ch.base.dto.market.Component;
import com.zifangdt.ch.market.validation.UserOrgaExist;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionDraftBo {
    private Long id;
    private String name;
    private PromotionTypeEnum type;
    private List<String> keys;

    @UserOrgaExist
    private List<UserOrga> userOrgas;

    private List<Component> components;

    private String extra;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PromotionTypeEnum getType() {
        return type;
    }

    public void setType(PromotionTypeEnum type) {
        this.type = type;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<UserOrga> getUserOrgas() {
        return userOrgas;
    }

    public void setUserOrgas(List<UserOrga> userOrgas) {
        this.userOrgas = userOrgas;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
