package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class ConfigItemVerboseFetcher extends AbstractVerboseFetcher<Long, ConfigItem> {

    @Autowired(required = false)
    private AbstractConfigItemService configItemService;

    @Override
    protected List<ConfigItem> doFetch(Collection<Long> ids) {
        return configItemService.getListForInternal(new ArrayList<>(ids));
    }

    @Override
    public String nameOf(ConfigItem verbose) {
        return ((DetailOfOption) verbose.getDetail()).getName();
    }

    public String nameOf(Long optionId) {
        return nameOf(cachedOne(optionId));
    }
}
