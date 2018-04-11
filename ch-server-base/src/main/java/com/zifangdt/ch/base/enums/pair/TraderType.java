package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.api.CustomerServerApi;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.customer.Partner;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.entity.Provider;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.util.ApplicationContextUtil;
import com.zifangdt.ch.base.web.UserVerboseFetcher;

import java.util.function.Function;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public enum TraderType implements PairedEnum {
    CUSTOMER(1, "客户", new TraderBehavior<Customer>(
            id -> ApplicationContextUtil.getBean(CustomerServerApi.class).getOneCustomer(id),
            customer -> customer.getName() + "," + customer.getPhone()
    )),
    STAFF(2, "员工", new TraderBehavior<User>(
            id -> ApplicationContextUtil.getBean(UserVerboseFetcher.class).one(id),
            user -> user.getName() + "," + user.getPhone()
    )),
    PROVIDER(3, "供应商", new TraderBehavior<Provider>(
            id -> null,
            provider -> provider.getName() + "," + provider.getPhone()
    )),
    PARTNER(4, "合作伙伴", new TraderBehavior<Partner>(
            id -> ApplicationContextUtil.getBean(CustomerServerApi.class).getPartner(id),
            partner -> partner.getName() + "," + partner.getContactPhone()
    ));

    final int intVerifier;
    final String name;
    final TraderBehavior<?> traderBehavior;

    TraderType(int intVerifier, String name, TraderBehavior<?> traderBehavior) {
        this.intVerifier = intVerifier;
        this.name = name;
        this.traderBehavior = traderBehavior;
    }

    public TraderBehavior<?> getTraderBehavior() {
        return traderBehavior;
    }

    public static class TraderBehavior<T> {
        protected final Function<Long, T> fetcher;
        protected final Function<T, String> infoGetter;

        TraderBehavior(Function<Long, T> fetcher, Function<T, String> infoGetter) {
            this.fetcher = fetcher;
            this.infoGetter = infoGetter;
        }

        public void checkTrader(Journal journal) {
            T t = fetcher.apply(journal.getTraderId());
            if (t == null) {
                throw new DataInvalidException("所关联的交易方不存在");
            }
            journal.setTraderInfo(infoGetter.apply(t));
        }
    }
}
