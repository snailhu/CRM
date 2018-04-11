package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.QuoteServerApi;
import com.zifangdt.ch.base.dto.quote.QuoteBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class BillVerboseFetcher extends AbstractVerboseFetcher<Long, QuoteBill> {

    @Autowired
    private QuoteServerApi quoteServerApi;

    @Override
    protected List<QuoteBill> doFetch(Collection<Long> ids) {
        return quoteServerApi.getQuoteBillById(new ArrayList<>(ids));
    }

    @Override
    public String nameOf(QuoteBill verbose) {
        return verbose.getBillName();
    }

    public String namesOf(List<Long> ids) {
        List<QuoteBill> bills = fetch(ids);
        return bills.stream().map(QuoteBill::getBillName).collect(Collectors.joining(","));
    }
}
