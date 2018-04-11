package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.dto.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class ContractVerboseFetcher extends AbstractVerboseFetcher<Long, Contract> {

    @Autowired
    private ContractServerApi contractServerApi;

    @Override
    protected List<Contract> doFetch(Collection<Long> ids) {
        return contractServerApi.getDetails(new HashSet<>(ids));
    }

    @Override
    public String nameOf(Contract verbose) {
        return verbose.getPrintNumber();
    }
}
