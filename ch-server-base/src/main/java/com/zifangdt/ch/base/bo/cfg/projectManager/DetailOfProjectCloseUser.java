package com.zifangdt.ch.base.bo.cfg.projectManager;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoiceWithOwner;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

public class DetailOfProjectCloseUser extends DetailOfUserChoiceWithOwner {
    private Boolean projectManagerInvalid;

    public Boolean getProjectManagerInvalid() {
        return projectManagerInvalid;
    }

    public void setProjectManagerInvalid(Boolean projectManagerInvalid) {
        this.projectManagerInvalid = projectManagerInvalid;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (projectManagerInvalid == null) {
            projectManagerInvalid = Boolean.FALSE;
        }
    }
}