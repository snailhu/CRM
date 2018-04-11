package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.util.DateUtil;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/3/13.
 */
public enum CustomerExportField implements PairedEnum {

    ID(1, "客户ID") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return customer.getId();
        }
    },
    NAME(2, "客户名称") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return customer.getName();
        }
    },
    OWNER(3, "客户所有人") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return customer.getOwnerName();
        }
    },
    SOURCE(4, "客户来源") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return fetcher.nameOf(customer.getSource());
        }
    },
    STAGE(5, "客户阶段") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return customer.getStage().getName();
        }
    },
    STAT(6, "客户分析") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            List<String> stat = new ArrayList<>(2);
            if (customer.getWorth() != null) {
                stat.add(customer.getWorth().getName());
            }
            if (customer.getOrderSize() != null) {
                stat.add(customer.getOrderSize().getName());
            }
            return stat.isEmpty() ? "" : stat.stream().collect(Collectors.joining(","));
        }
    },
    CREATE_TIME(7, "创建日期") {
        @Override
        public Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher) {
            return DateUtil.format(customer.getCreateTime(), "yyyy-MM-dd");
        }
    };

    final int intVerifier;
    final String name;

    CustomerExportField(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public abstract Object getCellValue(Customer customer, ConfigItemVerboseFetcher fetcher);
}
