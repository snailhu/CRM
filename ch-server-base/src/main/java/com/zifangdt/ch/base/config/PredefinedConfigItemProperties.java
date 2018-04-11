package com.zifangdt.ch.base.config;

import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/17.
 */
@Component
@ConfigurationProperties
public class PredefinedConfigItemProperties {
    private Map<ConfigType, List<String[]>> predefinedConfigs;
    @Autowired
    private ConfigItemMapper configItemMapper;

    public Map<ConfigType, List<String[]>> getPredefinedConfigs() {
        return predefinedConfigs;
    }

    public void setPredefinedConfigs(Map<ConfigType, List<String[]>> predefinedConfigs) {
        this.predefinedConfigs = predefinedConfigs;
    }

    public List<ConfigItem> init() {
        if (predefinedConfigs == null) {
            return null;
        }
        return predefinedConfigs.entrySet().stream()
                .filter(e -> !configItemMapper.typeExists(e.getKey()))
                .flatMap(e -> ClassUtil.newInstance(e.getKey().getBoClass()).parse(e.getKey(), e.getValue()).stream()).collect(Collectors.toList());
    }
}
