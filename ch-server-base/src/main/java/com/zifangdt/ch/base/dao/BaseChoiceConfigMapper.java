package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.dto.BaseChoiceConfig;

public interface BaseChoiceConfigMapper<T extends BaseChoiceConfig> extends BaseMapper<T>{
    T findByName(String name);
}
