package com.zifangdt.ch.contract.service.cfg;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.IdAndNameOfMutex;
import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfContractType;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfProcedure;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;
import com.zifangdt.ch.contract.mapper.cfg.LocalConfigItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/6.
 */
@Service
public class ConfigItemService extends AbstractConfigItemService {
    @Autowired
    private ConfigItemMapper configItemMapper;
    @Autowired
    private LocalConfigItemMapper localConfigItemMapper;
    @Autowired
    private ConfigItemVerboseFetcher configItemVerboseFetcher;

    @Override
    @SuppressWarnings("unchecked")
    protected void postProcessResult(Map<ConfigType, Object> result) {
        result.put(ConfigType.contractFlowSteps, sortedFlowSteps((List<DetailOfFlowStep>) result.get(ConfigType.contractFlowSteps)));
    }

    @Override
    protected boolean isHiddenOption(ConfigType configType, DetailOfOption option) {
        return option.getDisabled();
    }

    @Override
    protected void preUpdate(ConfigType configType, BaseConfigDetail param, BaseConfigDetail exist) {
        if (configType == ConfigType.procedures) {
            DetailOfProcedure existProcedure = (DetailOfProcedure) exist;
            DetailOfProcedure paramProcedure = (DetailOfProcedure) param;
            if (!existProcedure.getName().equals(paramProcedure.getName())
                    || !existProcedure.getDescription().equals(paramProcedure.getDescription())) {
                localConfigItemMapper.updateVersionByProcedureId(System.currentTimeMillis(), exist.getId());
            }

        }
    }

    @Override
    protected void doDeleteOption(DetailOfOption option) {
        configItemMapper.disable(option.getId());
    }

    @Override
    protected void postDeleteOption(ConfigType configType, DetailOfOption option) {
        if (configType == ConfigType.procedures) {
            List<ConfigItem> configItems = localConfigItemMapper.selectContractTypesByProcedureId(option.getId());
            if (!CollectionUtils.isEmpty(configItems)) {
                for (ConfigItem item : configItems) {
                    DetailOfContractType type = (DetailOfContractType) item.getDetail();
                    type.getProcedures().remove(option.getId());
                    type.setVersion(System.currentTimeMillis());
                    item.setType(null);
                    configItemMapper.updateByPrimaryKeySelective(item);
                }
            }
        }

    }

    public List<IdAndName> findProcedures(Long contractType) {
        List<IdAndName> options = options(ConfigType.procedures);
        if (contractType == null) {
            return options;
        } else {
            BaseConfigDetail detail = detail(contractType);
            if (detail == null) {
                return new ArrayList<>();
            }
            DetailOfContractType type = (DetailOfContractType) detail;
            Map<Long, IdAndName> map = options.stream().collect(Collectors.toMap(IdAndName::getId, Function.identity()));
            return type.getProcedures().stream().map(map::get).filter(Objects::nonNull).collect(Collectors.toList());
        }

    }

    @Override
    protected IdAndName fromOption(DetailOfOption option) {
        if (option instanceof DetailOfContractType) {
            DetailOfContractType contractType = (DetailOfContractType) option;
            IdAndNameOfMutex idAndNameOfMutex = new IdAndNameOfMutex();
            idAndNameOfMutex.setId(option.getId());
            idAndNameOfMutex.setName(option.getName());
            idAndNameOfMutex.setMutex(contractType.getMutuallyExclusive());
            return idAndNameOfMutex;
        } else {
            return super.fromOption(option);
        }
    }

    public String optionName(Long id) {
        ConfigItem item = configItemVerboseFetcher.one(id);
        if (item == null || !(item.getDetail() instanceof DetailOfOption)) {
            return null;
        }
        return ((DetailOfOption) item.getDetail()).getName();
    }

    public Map<Long, String> optionNames(Set<Long> ids) {
        List<ConfigItem> items = configItemVerboseFetcher.fetch(ids);
        return items.stream().map(item -> (DetailOfOption) detailWithId(item))
                .collect(Collectors.toMap(DetailOfOption::getId, DetailOfOption::getName));
    }

    @Override
    protected void bulkSaveRequiredConfigItems(List<ConfigItem> requiredConfigItems) {
        Map<ConfigType, List<ConfigItem>> group = requiredConfigItems.stream().collect(Collectors.groupingBy(ConfigItem::getType));
        List<ConfigItem> contractTypes = group.get(ConfigType.contractTypes);
        List<ConfigItem> procedures = group.get(ConfigType.procedures);
        if (CollectionUtils.isEmpty(contractTypes) || CollectionUtils.isEmpty(procedures)) {
            configItemMapper.insertList(requiredConfigItems);
            return;
        }
        configItemMapper.insertList(requiredConfigItems.stream().filter(n -> n.getType() != ConfigType.contractTypes).collect(Collectors.toList()));
        Map<String, Long> procedureMap = procedures.stream().collect(Collectors.toMap(n -> ((DetailOfProcedure) n.getDetail()).getName(), ConfigItem::getId));
        contractTypes.forEach(n -> {
            DetailOfContractType contractType = (DetailOfContractType) n.getDetail();
            List<Long> procedureIds = contractType.getProcedureNames().stream().map(procedureMap::get).collect(Collectors.toList());
            contractType.setProcedures(procedureIds);
            contractType.setProcedureNames(null);
        });
        configItemMapper.insertList(contractTypes);
    }

}
