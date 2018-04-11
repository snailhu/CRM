package com.zifangdt.ch.uaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 袁兵 on 2018/2/2.
 */
@Component
@ConfigurationProperties
public class PredefinedMenuProperties {
    private Map<String, Map<String, List<String>>> predefinedMenus;

    public Map<String, Map<String, List<String>>> getPredefinedMenus() {
        return predefinedMenus;
    }

    public void setPredefinedMenus(Map<String, Map<String, List<String>>> predefinedMenus) {
        this.predefinedMenus = predefinedMenus;
    }
}
