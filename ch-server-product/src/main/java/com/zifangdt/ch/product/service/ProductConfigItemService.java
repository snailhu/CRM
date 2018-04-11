package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.enums.ApprovedBy;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import com.zifangdt.ch.base.util.WebUtil;
import com.zifangdt.ch.base.web.UserVerboseFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductConfigItemService extends AbstractConfigItemService{


    @Autowired
    private UserVerboseFetcher fetcher;

    @Autowired
    private UaaServerApi uaaServerApi;

    void customChoiceList(DetailOfFlowStep step) {
        if (WebUtil.isFeignRequest() || CollectionUtils.isEmpty(step.getChoices())) {
            return;
        }
        if (step.getApprovedBy() == ApprovedBy.USERS) {
            step.setChoiceList(fetcher.fetch(step.getChoices()).stream().map(user -> new IdAndName(user.getId(), user.getName())).collect(Collectors.toList()));
        } else if (step.getApprovedBy() == ApprovedBy.ORGANIZATION_HEAD) {
            Organization organization = uaaServerApi.getOneOrganization(step.getChoices().iterator().next());
            step.setChoiceList(Collections.singletonList(new IdAndName(organization.getId(), organization.getName())));
        }
    }

    @Override
    public List<DetailOfFlowStep> steps(ConfigType configType) {
        List<DetailOfFlowStep> result = super.steps(configType);
        result.forEach(this::customChoiceList);
        return result;
    }

}
