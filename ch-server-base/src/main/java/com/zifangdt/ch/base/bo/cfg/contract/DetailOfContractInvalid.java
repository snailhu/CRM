package com.zifangdt.ch.base.bo.cfg.contract;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoiceWithOwner;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

/**
 * Created by 袁兵 on 2018/1/17.
 */
public class DetailOfContractInvalid extends DetailOfUserChoiceWithOwner {
    private Boolean closeProjectMeanwhile;

    public Boolean getCloseProjectMeanwhile() {
        return closeProjectMeanwhile;
    }

    public void setCloseProjectMeanwhile(Boolean closeProjectMeanwhile) {
        this.closeProjectMeanwhile = closeProjectMeanwhile;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (closeProjectMeanwhile == null) {
            closeProjectMeanwhile = Boolean.FALSE;
        }
    }
}
