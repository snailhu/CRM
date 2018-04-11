package com.zifangdt.ch.base.bo.cfg.base;

import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/8.
 */
public abstract class BaseConfigDetail {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {

    }

    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {

    }

    public List<ConfigItem> parse(ConfigType configType,List<String[]> lines){
        throw new WrongOperationException("need implement");
    }
}
