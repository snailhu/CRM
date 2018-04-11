package com.zifangdt.ch.base.bo.cfg.base;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.SimpleUserInfoPostProcessor;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/8.
 */
public class DetailOfUserChoice extends BaseConfigDetail {

    @VerboseProperty(target = JsonPropertyTarget.USER, postProcessor = SimpleUserInfoPostProcessor.class)
    private Set<Long> users;

    public Set<Long> getUsers() {
        return users;
    }

    public void setUsers(Set<Long> users) {
        this.users = users;
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (users == null) {
            users = new HashSet<>();
        }
    }
}
