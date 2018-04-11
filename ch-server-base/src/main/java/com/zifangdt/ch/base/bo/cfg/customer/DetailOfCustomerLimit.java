package com.zifangdt.ch.base.bo.cfg.customer;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

/**
 * Created by 袁兵 on 2018/1/23.
 */
public class DetailOfCustomerLimit extends BaseConfigDetail {
    private Boolean disabled;
    private Integer max;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        if (disabled == null) {
            disabled = Boolean.FALSE;
        }
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        if (max != null && max < 0) {
            throw new DataInvalidException("上限不能为负数");
        }
    }
}
