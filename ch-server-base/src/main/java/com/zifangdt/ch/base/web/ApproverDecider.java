package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.*;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.enums.pair.ProcessType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Component
public class ApproverDecider {
    @Autowired
    private FinanceServerApi financeServerApi;
    @Autowired
    private UaaServerApi uaaServerApi;
    @Autowired
    private ContractVerboseFetcher contractVerboseFetcher;
    @Autowired
    private QuoteServerApi quoteServerApi;
    @Autowired
    private ManagerServerApi managerServerApi;
    @Autowired
    private ProductServerApi productServerApi;

    public Set<Long> decide(DetailOfFlowStep step, ProcessInstance processInstance) {
        switch (step.getApprovedBy()) {
            case NONE:
                return new HashSet<>(Collections.singletonList(processInstance.getInitiator()));
            case USERS:
                return decideUsers(step);
            case CONTRACT_OWNER:
                return decideContractOwner(step,processInstance);
            case FINANCIAL_STAFF:
                return decideFinancialStaff();
            case PROJECT_MANAGER:
                return decideProjectManager(processInstance);
            case PROJECT_OWNER:
                return decideProjectOwner(processInstance);
            case QUOTE_CREATOR:
                return decideQuoteOwner(processInstance) ;
            case ORGANIZATION_HEAD:
                return decideOrganizationHead(step);
        }
        return null;
    }

    private Set<Long> decideFinancialStaff() {
        return financeServerApi.financialStaff();
    }

    private Set<Long> decideOrganizationHead(DetailOfFlowStep step) {
        if (CollectionUtils.isEmpty(step.getChoices())) {
            throw new IllegalArgumentException("该配置项尚未选择任何部门负责人");
        }
        Organization organization = uaaServerApi.getOrganization(step.getChoices().iterator().next());
        if (organization == null) {
            throw new DataInvalidException("所选部门不存在");
        }
        if (organization.getHeadId() == null) {
            throw new DataInvalidException("所选部门没有负责人");
        }
        Set<Long> set = new HashSet<>();
        set.add(organization.getHeadId());
        return set;
    }

    private Set<Long> decideUsers(DetailOfFlowStep step) {
        return step.getChoices();
    }

    private Set<Long> decideContractOwner(DetailOfFlowStep step, ProcessInstance processInstance) {
        if (processInstance.getType() == ProcessType.CONTRACT) {
            Contract one = contractVerboseFetcher.one(processInstance.getObject());
            if (one == null) {
                throw new DataInvalidException(String.format("当前合同(id=%s)不存在", processInstance.getObject()));
            }
            if (one.getOwner() == null) {
                throw new DataInvalidException(String.format("当前合同(id=%s)没有所有人", processInstance.getObject()));
            }
            Set<Long> set = new HashSet<>();
            set.add(one.getOwner());
            return set;
        } else {
            //TODO
        }
        return null;
    }

    private Set<Long> decideQuoteOwner( ProcessInstance processInstance) {
        if (processInstance.getType() == ProcessType.BILL) {
            Long ownerId = quoteServerApi.getOwnerId(processInstance.getObject());
            Set<Long> set = new HashSet<>();
            set.add(ownerId);
            return set;
        } else {
            //TODO
        }
        return null;
    }

    private Set<Long> decideProjectOwner(ProcessInstance processInstance) {
        if (processInstance.getType() == ProcessType.CONSTRUCT_SCHEME) {
            Long ownerId = managerServerApi.getProjectOwner(processInstance.getObject());
            Set<Long> set = new HashSet<>();
            set.add(ownerId);
            return set;
        } else {

        }
        return null;
    }
    private Set<Long> decideProjectManager(ProcessInstance processInstance) {
        if (processInstance.getType() == ProcessType.CONSTRUCT_SCHEME) {
            Set<Long> set = managerServerApi.getProjectManager();
//            Set<Long> set = new HashSet<>();
//            set.add(ownerId);
            return set;
        } else {
            //TODO
        }
        return null;
    }

    private Set<Long> decidePurchaseOwner(DetailOfFlowStep step, ProcessInstance processInstance) {
        Long ownerId = productServerApi.getOne(processInstance.getObject()).getCreateId();
        return new HashSet<>(Arrays.asList(ownerId));
    }
}
