package com.zifangdt.ch.base.bo.cfg.customer;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.CustomerSourceRelationType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public class DetailOfCustomerSource extends DetailOfOption {
    @NamedProperty
    private CustomerSourceRelationType relationType;

    public DetailOfCustomerSource() {
    }

    public DetailOfCustomerSource(String name, CustomerSourceRelationType relationType) {
        super(name);
        this.relationType = relationType;
    }

    public CustomerSourceRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(CustomerSourceRelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        return lines.stream().map(line -> new ConfigItem(configType, new DetailOfCustomerSource(line[0], CustomerSourceRelationType.valueOf(line[1])).enabled())).collect(Collectors.toList());
    }

    @Override
    protected boolean nameExists(ConfigType configType, ConfigItemMapper configItemMapper) {
        return configItemMapper.nameExistsAndEnabled(configType, getName());
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (relationType == null
                || relationType == CustomerSourceRelationType.CREATOR_CUSTOMIZATION
                || relationType == CustomerSourceRelationType.CLUE
                || relationType == CustomerSourceRelationType.MARKET) {
            throw new DataInvalidException("请指定正确的关联类型");
        }
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        super.preUpdate(configType, configItemMapper, exist);
        DetailOfCustomerSource existType = (DetailOfCustomerSource) exist;
        if (existType.relationType == CustomerSourceRelationType.CREATOR_CUSTOMIZATION) {
            relationType = null;
        } else if (existType.relationType == CustomerSourceRelationType.CLUE
                || existType.relationType == CustomerSourceRelationType.MARKET) {
            throw new DataInvalidException("无法编辑该配置项");
        } else if (relationType == null
                || relationType == CustomerSourceRelationType.CREATOR_CUSTOMIZATION
                || relationType == CustomerSourceRelationType.CLUE
                || relationType == CustomerSourceRelationType.MARKET) {
            throw new DataInvalidException("请指定正确的关联类型");
        }
    }
}
