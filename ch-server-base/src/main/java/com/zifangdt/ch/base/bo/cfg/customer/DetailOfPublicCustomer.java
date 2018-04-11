package com.zifangdt.ch.base.bo.cfg.customer;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class DetailOfPublicCustomer extends BaseConfigDetail {
    private Boolean disabled;
    private Integer maxPerPerson;
    private Boolean maxPerPersonDisabled;
    private Integer daysUnmodified;
    private Boolean daysUnmodifiedDisabled;
    private Integer daysUnsigned;
    private Boolean daysUnsignedDisabled;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getMaxPerPerson() {
        return maxPerPerson;
    }

    public void setMaxPerPerson(Integer maxPerPerson) {
        this.maxPerPerson = maxPerPerson;
    }

    public Boolean getMaxPerPersonDisabled() {
        return maxPerPersonDisabled;
    }

    public void setMaxPerPersonDisabled(Boolean maxPerPersonDisabled) {
        this.maxPerPersonDisabled = maxPerPersonDisabled;
    }

    public Integer getDaysUnmodified() {
        return daysUnmodified;
    }

    public void setDaysUnmodified(Integer daysUnmodified) {
        this.daysUnmodified = daysUnmodified;
    }

    public Boolean getDaysUnmodifiedDisabled() {
        return daysUnmodifiedDisabled;
    }

    public void setDaysUnmodifiedDisabled(Boolean daysUnmodifiedDisabled) {
        this.daysUnmodifiedDisabled = daysUnmodifiedDisabled;
    }

    public Integer getDaysUnsigned() {
        return daysUnsigned;
    }

    public void setDaysUnsigned(Integer daysUnsigned) {
        this.daysUnsigned = daysUnsigned;
    }

    public Boolean getDaysUnsignedDisabled() {
        return daysUnsignedDisabled;
    }

    public void setDaysUnsignedDisabled(Boolean daysUnsignedDisabled) {
        this.daysUnsignedDisabled = daysUnsignedDisabled;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        if (disabled == null) {
            disabled = Boolean.FALSE;
        }
        if (maxPerPersonDisabled == null) {
            maxPerPersonDisabled = Boolean.FALSE;
        }
        if (daysUnsignedDisabled == null) {
            daysUnsignedDisabled = Boolean.FALSE;
        }
        if (daysUnmodifiedDisabled == null) {
            daysUnmodifiedDisabled = Boolean.FALSE;
        }
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        if (maxPerPerson != null && maxPerPerson < 0) {
            throw new DataInvalidException("上限不能为负数");
        }
        if (daysUnmodified != null && daysUnmodified < 1) {
            throw new DataInvalidException("天数必须至少为1天");
        }
        if (daysUnsigned != null && daysUnsigned < 1) {
            throw new DataInvalidException("天数必须至少为1天");
        }
    }
}
