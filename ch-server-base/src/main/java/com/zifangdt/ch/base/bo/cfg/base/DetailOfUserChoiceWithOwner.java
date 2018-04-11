package com.zifangdt.ch.base.bo.cfg.base;

import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class DetailOfUserChoiceWithOwner extends DetailOfUserChoice {
    private Boolean ownerIncluded;

    public Boolean getOwnerIncluded() {
        return ownerIncluded;
    }

    public void setOwnerIncluded(Boolean ownerIncluded) {
        this.ownerIncluded = ownerIncluded;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (ownerIncluded == null) {
            ownerIncluded = Boolean.FALSE;
        }
    }
}
