package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.dto.quote.QuoteBill;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public class SimpleBillInfoPostProcessor implements VerboseInfoPostProcessor<QuoteBill> {
    @Override
    public List<QuoteBill> postProcess(List<QuoteBill> list) {
        return list.stream().map(user -> {
            QuoteBill b = new QuoteBill();
            b.setId(user.getId());
            b.setBillName(user.getBillName());
            return b;
        }).collect(Collectors.toList());
    }
}
