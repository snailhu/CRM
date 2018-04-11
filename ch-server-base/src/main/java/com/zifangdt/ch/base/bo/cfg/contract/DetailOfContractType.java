package com.zifangdt.ch.base.bo.cfg.contract;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/17.
 */
public class DetailOfContractType extends DetailOfOption {
    private String description;
    private Long version;
    @VerboseProperty(target = JsonPropertyTarget.OPTION)
    private List<Long> procedures;
    private Boolean mutuallyExclusive;

    private List<String> procedureNames;

    public List<String> getProcedureNames() {
        return procedureNames;
    }

    public void setProcedureNames(List<String> procedureNames) {
        this.procedureNames = procedureNames;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Long> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Long> procedures) {
        this.procedures = procedures;
    }

    public Boolean getMutuallyExclusive() {
        return mutuallyExclusive;
    }

    public void setMutuallyExclusive(Boolean mutuallyExclusive) {
        this.mutuallyExclusive = mutuallyExclusive;
    }

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
        version = System.currentTimeMillis();
        if (mutuallyExclusive == null) {
            mutuallyExclusive = Boolean.FALSE;
        }

        checkProcedures(configItemMapper);
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        super.preUpdate(configType, configItemMapper, exist);
        setDisabled(null);

        DetailOfContractType existType = (DetailOfContractType) exist;
        if (Boolean.TRUE.equals(existType.getDisabled())) {
            throw new WrongOperationException("该类型已被删除");
        }
        checkProcedures(configItemMapper);

        if (!existType.getName().equals(getName())
                || !StringUtils.join(existType.getProcedures(), ",").equals(StringUtils.join(getProcedures(), ","))) {
            version = System.currentTimeMillis();
        }
    }

    private void checkProcedures(ConfigItemMapper configItemMapper) {
        if (CollectionUtils.isEmpty(procedures)) {
            throw new DataInvalidException("必须至少选择一个工序");
        }
        List<ConfigItem> items = configItemMapper.getListByType(ConfigType.procedures);
        List<Long> ids = items.stream()
                .filter(a -> Boolean.FALSE.equals(((DetailOfOption) a.getDetail()).getDisabled()))
                .map(ConfigItem::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long procedure : procedures) {
            if (!ids.contains(procedure)) {
                throw new DataInvalidException("所选择的工序不存在:id=" + procedure);
            }
        }
    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        long version = System.currentTimeMillis();
        return lines.stream().map(line -> {
            DetailOfContractType contractType = new DetailOfContractType();
            contractType.setName(line[0]);
            contractType.setProcedureNames(Arrays.asList(line[1].split("#")));
            contractType.setDisabled(false);
            contractType.setVersion(version);
            contractType.setMutuallyExclusive(false);
            return new ConfigItem(configType, contractType);
        }).collect(Collectors.toList());
    }
}
