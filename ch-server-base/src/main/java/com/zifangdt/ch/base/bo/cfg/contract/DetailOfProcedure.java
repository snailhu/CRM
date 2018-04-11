package com.zifangdt.ch.base.bo.cfg.contract;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/17.
 */
public class DetailOfProcedure extends DetailOfOption {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected boolean nameExists(ConfigType configType, ConfigItemMapper configItemMapper) {
        return configItemMapper.nameExistsAndEnabled(configType, getName());
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        description = StringUtils.trimToEmpty(description);
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        super.preUpdate(configType, configItemMapper, exist);
        setDisabled(null);
        description = StringUtils.trimToEmpty(description);

        DetailOfProcedure existProcedure = (DetailOfProcedure) exist;
        if (Boolean.TRUE.equals(existProcedure.getDisabled())) {
            throw new WrongOperationException("该工序已被删除");
        }
    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        return lines.stream().map(line -> {
            DetailOfProcedure procedure = new DetailOfProcedure();
            procedure.setName(line[0]);
            procedure.setDisabled(false);
            procedure.setDescription("");
            return new ConfigItem(configType, procedure);
        }).collect(Collectors.toList());
    }
}
