package com.zifangdt.ch.base.service.cfg;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.bo.BatchUpdateBo;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.cfg.base.*;
import com.zifangdt.ch.base.config.PredefinedConfigItemProperties;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.ApprovedBy;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.Module;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.util.*;
import com.zifangdt.ch.base.web.UserVerboseFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/6.
 */
@Service
@Transactional
public abstract class AbstractConfigItemService {
    @Autowired
    private ConfigItemMapper configItemMapper;
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private PredefinedConfigItemProperties predefinedConfigItemProperties;
    @Autowired
    private UaaServerApi uaaServerApi;
    @Autowired
    private UserVerboseFetcher fetcher;

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Module module() {
        return Module.valueOf(applicationName
                .replace(Constants.APPLICATION_NAME_SUFFIX, "")
                .replace("-", "")
                .toUpperCase());
    }

    @PostConstruct
    public void init() {
        Module module = module();
        List<ConfigType> singleConfigTypes = Arrays.stream(ConfigType.values()).filter(t -> t.getModule() == module && !t.isMulti()).collect(Collectors.toList());

        List<ConfigItem> requiredConfigItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(singleConfigTypes)) {
            for (ConfigType configType : singleConfigTypes) {
                if (!configItemMapper.typeExists(configType)) {
                    BaseConfigDetail detail = ClassUtil.newInstance(configType.getBoClass());
                    detail.preSave(configType, configItemMapper);
                    requiredConfigItems.add(new ConfigItem(configType, detail));
                }
            }
        }

        List<ConfigItem> configured = predefinedConfigItemProperties.init();
        if (!CollectionUtils.isEmpty(configured)) {
            requiredConfigItems.addAll(configured);
        }

        if (!CollectionUtils.isEmpty(requiredConfigItems)) {
            bulkSaveRequiredConfigItems(requiredConfigItems);
        }
    }

    protected void bulkSaveRequiredConfigItems(List<ConfigItem> requiredConfigItems) {
        configItemMapper.bulkInsert(requiredConfigItems);
    }

    public void saveOrUpdate(ConfigType configType, String json) {
        BaseConfigDetail detail = JsonUtil.parse(json, configType.getBoClass());

        Long id = detail.getId();
        detail.setId(null);

        ConfigItem configItem = null;
        if (configType.isMulti()) {
            if (id == null) {
                detail.preSave(configType, configItemMapper);
                preSave(configType, detail);

                ConfigItem insert = new ConfigItem();
                insert.setType(configType);
                insert.setDetail(detail);
                configItemMapper.insert(insert);
                return;
            } else {
                configItem = configItemMapper.selectByPrimaryKey(id);
            }
        } else {
            configItem = configItemMapper.getOneByType(configType);
        }
        if (configItem == null) {
            throw new DataNotFoundException("未找到该配置项");
        }

        if (BeanUtil.hasNonNullProperty(detail)) {
            BaseConfigDetail detailWithId = detailWithId(configItem);
            detail.preUpdate(configType, configItemMapper, detailWithId);
            preUpdate(configType, detail, detailWithId);

            BeanUtil.copyNonNullProperties(detail, detailWithId);
            detailWithId.setId(null);
            ConfigItem update = new ConfigItem();
            update.setId(configItem.getId());
            update.setDetail(detailWithId);
            configItemMapper.updateByPrimaryKeySelective(update);
        }

    }

    protected List<ConfigItem> getAll() {
        return configItemMapper.selectAll();
    }

    protected boolean isHiddenOption(ConfigType configType, DetailOfOption option) {
        return false;
    }

    protected boolean visibleItem(ConfigItem configItem) {
        return !(configItem.getDetail() instanceof DetailOfOption)
                || !isHiddenOption(configItem.getType(), (DetailOfOption) configItem.getDetail());
    }

    public Map<ConfigType, Object> all() {
        List<ConfigItem> all = getAll();
        all = all.stream().filter(this::visibleItem).collect(Collectors.toList());
        Map<ConfigType, Object> result = new HashMap<>();
        for (ConfigItem configItem : all) {
            BaseConfigDetail detail = detailWithId(configItem);

            if (configItem.getType().isMulti()) {
                result.compute(configItem.getType(), (k, v) -> {
                    if (v == null) {
                        return new ArrayList<>(Collections.singletonList(detail));
                    }
                    @SuppressWarnings("unchecked")
                    List<BaseConfigDetail> list = (List<BaseConfigDetail>) v;
                    list.add(detail);

                    if (detail instanceof DetailOfFlowStep) {
                        customChoiceList((DetailOfFlowStep) detail);
                    }
                    return list;
                });
            } else {
                result.put(configItem.getType(), detail);
            }
        }
        postProcessResult(result);
        return result;
    }

    protected void preSave(ConfigType configType, BaseConfigDetail detail) {
    }

    protected void preUpdate(ConfigType configType, BaseConfigDetail param, BaseConfigDetail exist) {
    }

    protected void postProcessResult(Map<ConfigType, Object> result) {
    }

    public Object byType(ConfigType configType) {
        if (configType.isMulti()) {
            return details(configType);
        } else {
            ConfigItem item = configItemMapper.getOneByType(configType);
            return detailWithId(item);
        }
    }

    protected BaseConfigDetail detailWithId(ConfigItem item) {
        BaseConfigDetail detail = item.getDetail();
        detail.setId(item.getId());
        if (item.getUsed() != null && detail instanceof DetailOfOption) {
            ((DetailOfOption) detail).setUsed(item.getUsed());
        }
        return detail;
    }

    public List<BaseConfigDetail> details(ConfigType configType) {
        List<ConfigItem> items = configItemMapper.getListByType(configType);
        items = items.stream().filter(this::visibleItem).collect(Collectors.toList());
        return items.stream()
                .map(this::detailWithId)
                .collect(Collectors.toList());
    }

    protected boolean optionFilter(IdAndName idAndName) {
        return true;
    }

    protected IdAndName fromOption(DetailOfOption option) {
        return IdAndName.fromOption(option);
    }

    public List<IdAndName> options(ConfigType configType) {
        List<IdAndName> result = new ArrayList<>();
        if (!configType.isMulti()) {
            return result;
        }

        List<BaseConfigDetail> details = details(configType);
        return details.stream().map(DetailOfOption.class::cast)
                .filter(a -> Boolean.FALSE.equals(a.getDisabled()))
                .map(this::fromOption)
                .filter(this::optionFilter)
                .collect(Collectors.toList());
    }

    protected void doDeleteOption(DetailOfOption option) {
        configItemMapper.deleteByPrimaryKey(option.getId());
    }

    public void deleteOption(Long id) {
        ConfigItem configItem = configItemMapper.selectByPrimaryKey(id);
        if (configItem == null) {
            throw new DataNotFoundException("未找到该配置项");
        }
        BaseConfigDetail detail = detailWithId(configItem);
        if (!(detail instanceof DetailOfOption)) {
            throw new WrongOperationException("无法删除该配置项");
        }
        DetailOfOption option = (DetailOfOption) detail;
        if (isHiddenOption(configItem.getType(), option)) {
            throw new DataNotFoundException("未找到或者无法删除该配置项");
        }
        preDeleteOption(option);

        doDeleteOption(option);

        postDeleteOption(configItem.getType(), option);
    }

    protected void preDeleteOption(DetailOfOption option) {
    }

    protected void postDeleteOption(ConfigType configType, DetailOfOption option) {
    }

    public List<DetailOfOption> configItems(Set<ConfigType> configTypes) {
        if (configTypes.stream().anyMatch(t -> !t.isMulti())) {
            throw new IllegalArgumentException("configType of non-option exists:" + configTypes);
        }

        List<ConfigItem> items = configItemMapper.getListByTypes(configTypes);
        return items.stream()
                .map(this::detailWithId)
                .map(DetailOfOption.class::cast)
                .filter(a -> Boolean.FALSE.equals(a.getDisabled()))
                .collect(Collectors.toList());
    }

    public List<ConfigItem> getListForInternal(List<Long> ids) {
        return configItemMapper.getListByIds(new HashSet<>(ids));
    }

    private void customChoiceList(DetailOfFlowStep step) {
        if (WebUtil.isFeignRequest() || CollectionUtils.isEmpty(step.getChoices())) {
            return;
        }
        if (step.getApprovedBy() == ApprovedBy.USERS) {
            step.setChoiceList(fetcher.fetch(step.getChoices()).stream().map(user -> new IdAndName(user.getId(), user.getName())).collect(Collectors.toList()));
        } else if (step.getApprovedBy() == ApprovedBy.ORGANIZATION_HEAD) {
            Organization organization = uaaServerApi.getOrganization(step.getChoices().iterator().next());
            step.setChoiceList(Collections.singletonList(new IdAndName(organization.getId(), organization.getName())));
        }
    }

    public List<DetailOfFlowStep> sortedFlowSteps(List<DetailOfFlowStep> steps) {
        if (steps == null) {
            return null;
        }
        DetailOfFlowStep first = null;
        for (DetailOfFlowStep step : steps) {
            if (step.getPrev() == null) {
                first = step;
                break;
            }
        }
        List<DetailOfFlowStep> sorted = new ArrayList<>();
        if (first != null) {
            Map<String, DetailOfFlowStep> map = steps.stream().collect(Collectors.toMap(DetailOfFlowStep::getUuid, Function.identity()));
            do {
                sorted.add(first);
                if (first.getNext() == null) {
                    break;
                }
                DetailOfFlowStep next = map.get(first.getNext());
                if (next == null) {
                    break;
                }
                if (!next.getPrev().equals(first.getUuid())) {
                    LOGGER.error("invalid step data");
                    break;
                }
                first = next;
            } while (true);
        }
        return sorted;
    }

    public void updateApprovers(String uuid, BatchUpdateBo bo) {
        ConfigItem configItem = configItemMapper.getStepByUuid(uuid);
        if (configItem == null) {
            throw new DataNotFoundException("该步骤不存在");
        }
        DetailOfFlowStep step = (DetailOfFlowStep) configItem.getDetail();
        if (step.getApprovedBy() == ApprovedBy.USERS) {
            userIdsValid(bo.getIds());
            configItemMapper.updateApprovers(configItem.getId(), bo.getIds());

            Set<Long> existChoices = step.getChoices();
            if (existChoices == null) {
                existChoices = new HashSet<>();
            }
            Set<Long> deletes = new HashSet<>(existChoices);
            deletes.removeAll(bo.getIds());
            uaaServerApi.updateApproverOf(new HashSet<>(bo.getIds()), deletes, configItem.getType().getModule());
        } else if (step.getApprovedBy() == ApprovedBy.ORGANIZATION_HEAD) {
            if (bo.getIds().size() > 1) {
                throw new WrongOperationException("只能为该步骤选择一个部门负责人");
            }
            Organization organization = uaaServerApi.getOrganization(bo.getIds().get(0));
            if (organization == null) {
                throw new DataInvalidException("所选部门不存在");
            }
            if (organization.getHeadId() == null) {
                throw new DataInvalidException("所选部门没有负责人");
            }

            configItemMapper.updateApprovers(configItem.getId(), bo.getIds());
        } else {
            throw new WrongOperationException("该步骤不支持修改审核人员");
        }
    }

    public List<DetailOfFlowStep> steps(ConfigType configType) {
        List<ConfigItem> items = configItemMapper.getListByType(configType);
        List<DetailOfFlowStep> result = items.stream().map(n -> (DetailOfFlowStep) detailWithId(n)).collect(Collectors.toList());
        return sortedFlowSteps(result);
    }

    public BaseConfigDetail detail(Long id) {
        ConfigItem configItem = configItemMapper.getOneById(id);
        return detailWithId(configItem);
    }

    public BaseConfigDetail detail(ConfigType configType) {
        ConfigItem configItem = configItemMapper.getOneByType(configType);
        return detailWithId(configItem);
    }

    public DetailOfUserChoice perms(ConfigType configType) {
        ConfigItem configItem = configItemMapper.getOneByType(configType);
        return (DetailOfUserChoice) detailWithId(configItem);
    }

    public Set<Long> permittedUsers(ConfigType configType) {
        DetailOfUserChoice perms = perms(configType);
        return perms.getUsers();
    }

    private void userIdsValid(List<Long> userIds) {
        List<User> users = fetcher.fetch(userIds);
        Set<Long> userIds1 = users.stream().map(User::getId).collect(Collectors.toSet());
        Set<Long> userIdsSet = new HashSet<>(userIds);
        userIdsSet.removeAll(userIds1);
        if (!userIdsSet.isEmpty()) {
            throw new DataInvalidException("用户id=" + userIds + "不存在");
        }
    }

    public List<IdAndName> optionsWithDisabled(ConfigType configType) {
        List<IdAndName> result = new ArrayList<>();
        if (!configType.isMulti()) {
            return result;
        }
        return configItemMapper.getListByType(configType)
                .stream()
                .map(this::detailWithId)
                .map(DetailOfOption.class::cast)
                .map(this::fromOption)
                .filter(this::optionFilter)
                .collect(Collectors.toList());
    }
}
