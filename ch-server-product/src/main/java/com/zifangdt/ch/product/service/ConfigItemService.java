package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoice;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.enums.ApprovedBy;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import com.zifangdt.ch.base.util.WebUtil;
import com.zifangdt.ch.base.web.UserVerboseFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConfigItemService extends AbstractConfigItemService{
    @Autowired
    private ConfigItemMapper configItemMapper;

    @Autowired
    private UaaServerApi uaaServerApi;

    @Autowired
    private UserVerboseFetcher fetcher;

    public Set<Long> getAllRepoStaff() {
        ConfigItem item = configItemMapper.getOneByType(ConfigType.RepoStaff);
        DetailOfUserChoice userChoice = (DetailOfUserChoice) item.getDetail();
        return userChoice.getUsers();
    }

    public List<DetailOfFlowStep> steps(ConfigType configType) {
        List<ConfigItem> items = configItemMapper.getListByType(configType);
        List<DetailOfFlowStep> result = items.stream().map(n -> (DetailOfFlowStep) detailWithId(n)).collect(Collectors.toList());
        for (DetailOfFlowStep step: result) {
            List<IdAndName> choiceLists = new ArrayList<>();
            step.setChoiceList(choiceLists);
        }
        result.forEach(this::customChoiceList);
        return sortedFlowSteps(result);
    }

    void customChoiceList(DetailOfFlowStep step) {
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
}
