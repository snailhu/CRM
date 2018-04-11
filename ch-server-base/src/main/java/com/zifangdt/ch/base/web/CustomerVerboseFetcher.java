package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.CustomerServerApi;
import com.zifangdt.ch.base.dto.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class CustomerVerboseFetcher extends AbstractVerboseFetcher<Long, Customer> {

    @Autowired
    private CustomerServerApi customerServerApi;

    @Override
    protected List<Customer> doFetch(Collection<Long> ids) {
        return customerServerApi.findList(new ArrayList<>(ids));
    }

    @Override
    public String nameOf(Customer verbose) {
        return verbose.getName();
    }
}
