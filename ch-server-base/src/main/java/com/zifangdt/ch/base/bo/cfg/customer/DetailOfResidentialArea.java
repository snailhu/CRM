package com.zifangdt.ch.base.bo.cfg.customer;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

/**
 * Created by 袁兵 on 2018/1/23.
 */
public class DetailOfResidentialArea extends BaseConfigDetail {
    private Boolean disabled;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        if (disabled == null) {
            disabled = Boolean.FALSE;
        }
    }
}
