package com.zifangdt.ch.base.bo.cfg.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/8.
 */
@JsonIgnoreProperties(value = "used", allowGetters = true)
public class DetailOfOption extends BaseConfigDetail {
    private Boolean disabled;
    private String name;
    private Boolean used;

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public DetailOfOption() {
    }

    public DetailOfOption(String name) {
        this.name = name;
    }

    public DetailOfOption enabled() {
        this.disabled = Boolean.FALSE;
        return this;
    }

    protected boolean nameExists(ConfigType configType, ConfigItemMapper configItemMapper) {
        return configItemMapper.nameExists(configType, name);
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        if (disabled == null) {
            disabled = Boolean.FALSE;
        }
        if (StringUtils.isEmpty(name)) {
            throw new DataInvalidException("必须指定配置项的名称");
        }
        if (nameExists(configType, configItemMapper)) {
            throw new DataInvalidException("名称已被使用");
        }
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        super.preUpdate(configType, configItemMapper, exist);
        DetailOfOption existType = (DetailOfOption) exist;
        if (name != null) {
            if (name.length() == 0) {
                throw new DataInvalidException("必须指定配置项的名称");
            }
            if (!name.equals(existType.getName()) && nameExists(configType, configItemMapper)) {
                throw new DataInvalidException("名称已被使用");
            }
        }

    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        return lines.stream().map(line -> new ConfigItem(configType, new DetailOfOption(line[0]).enabled())).collect(Collectors.toList());
    }
}
