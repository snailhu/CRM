package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.web.*;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public enum JsonPropertyTarget {
    DEFAULT(null),
    FILE(FileVerboseFetcher.class),
    USER(UserVerboseFetcher.class),
    CUSTOMER(CustomerVerboseFetcher.class),
    OPTION(ConfigItemVerboseFetcher.class),
    CONTRACT(ContractVerboseFetcher.class),
    BILL(BillVerboseFetcher.class),
    PRODUCT(ProductVerboseFetcher.class)
    ;

    private final Class<? extends AbstractVerboseFetcher> fetcherClass;

    JsonPropertyTarget(Class<? extends AbstractVerboseFetcher> fetcherClass) {
        this.fetcherClass=fetcherClass;
    }

    public Class<? extends AbstractVerboseFetcher> getFetcherClass() {
        return fetcherClass;
    }

}
