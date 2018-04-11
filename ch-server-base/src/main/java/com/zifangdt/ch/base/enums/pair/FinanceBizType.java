package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.api.CustomerServerApi;
import com.zifangdt.ch.base.api.ProductServerApi;
import com.zifangdt.ch.base.api.TicketServerApi;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.entity.Purchase;
import com.zifangdt.ch.base.dto.ticket.output.TicketDetailDto;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.util.ApplicationContextUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public enum FinanceBizType implements PairedEnum {
    CONTRACT(1, "合同", new BizBehavior<Contract>(
            id -> ApplicationContextUtil.getBean(ContractServerApi.class).simple(id),
            Contract::getNumber,
            Contract::getNumber,
            Contract::getTypes,
            Contract::getProjectId
    ) {
        @Override
        protected void initTraderBehaviors() {
            addBehavior(RevenueOrExpense.REVENUE, TraderType.CUSTOMER, Contract::getCustomerId);
            addBehavior(RevenueOrExpense.EXPENSE, TraderType.CUSTOMER, Contract::getCustomerId);
            addBehavior(RevenueOrExpense.EXPENSE, TraderType.STAFF, Contract::getOwner);
            addBehavior(RevenueOrExpense.EXPENSE, TraderType.PARTNER, contract -> ApplicationContextUtil.getBean(CustomerServerApi.class).getPartnerId(contract.getCustomerId()));
        }
    }),
    PURCHASE(2, "采购单", new BizBehavior<Purchase>(
            id -> ApplicationContextUtil.getBean(ProductServerApi.class).getOne(id),
            Purchase::getName,
            Purchase::getNum,
            null,
            null
    ) {
        @Override
        protected void initTraderBehaviors() {
            addBehavior(RevenueOrExpense.EXPENSE, TraderType.PROVIDER, Purchase::getProviderId);
        }

    }),
    TICKET(3, "工单", new BizBehavior<TicketDetailDto>(
            id -> ApplicationContextUtil.getBean(TicketServerApi.class).getDetail(id),
            TicketDetailDto::getSerialNumber,
            TicketDetailDto::getSerialNumber,
            ticket -> Collections.singletonList(ticket.getTicketTypeId()),
            TicketDetailDto::getProjectId
    ) {
        @Override
        protected void initTraderBehaviors() {
            addBehavior(RevenueOrExpense.REVENUE, TraderType.CUSTOMER, TicketDetailDto::getCustomerId);
            addBehavior(RevenueOrExpense.EXPENSE, TraderType.STAFF, TicketDetailDto::getOperatorId);
        }
    });

    final int intVerifier;
    final String name;
    final BizBehavior<?> bizBehavior;


    FinanceBizType(int intVerifier, String name, BizBehavior<?> bizBehavior) {
        this.intVerifier = intVerifier;
        this.name = name;
        this.bizBehavior = bizBehavior;
    }

    public BizBehavior<?> getBizBehavior() {
        return bizBehavior;
    }

    public static abstract class BizBehavior<T> {
        protected final Map<RevenueOrExpense, Map<TraderType, Function<T, Long>>> RELATED_TRADERS = new HashMap<>();
        protected final Function<T, String> bizNameGetter;
        protected final Function<T, String> bizNumberGetter;
        protected final Function<T, List<Long>> bizSelfTypesGetter;
        protected final Function<Long, T> fetcher;
        protected final Function<T, Long> projectIdGetter;

        BizBehavior(Function<Long, T> fetcher, Function<T, String> bizNameGetter, Function<T, String> bizNumberGetter, Function<T, List<Long>> bizSelfTypesGetter, Function<T, Long> projectIdGetter) {
            RELATED_TRADERS.put(RevenueOrExpense.REVENUE, new HashMap<>());
            RELATED_TRADERS.put(RevenueOrExpense.EXPENSE, new HashMap<>());
            initTraderBehaviors();
            this.fetcher = fetcher;
            this.bizNameGetter = bizNameGetter;
            this.bizNumberGetter = bizNumberGetter;
            this.bizSelfTypesGetter = bizSelfTypesGetter;
            this.projectIdGetter = projectIdGetter;
        }

        protected abstract void initTraderBehaviors();

        public boolean supports(RevenueOrExpense revenueOrExpense, TraderType traderType) {
            return RELATED_TRADERS.get(revenueOrExpense).containsKey(traderType);
        }

        public String supportedTraderTypeNames(RevenueOrExpense revenueOrExpense, TraderType traderType) {
            return RELATED_TRADERS.get(revenueOrExpense).keySet().stream().map(TraderType::getName).collect(Collectors.joining("、"));
        }

        public Long getTraderId(RevenueOrExpense revenueOrExpense, TraderType traderType, T biz) {
            return RELATED_TRADERS.get(revenueOrExpense).get(traderType).apply(biz);
        }

        protected void addBehavior(RevenueOrExpense revenueOrExpense, TraderType traderType, Function<T, Long> getter) {
            RELATED_TRADERS.get(revenueOrExpense).put(traderType, getter);
        }

        public void checkBiz(Journal journal) {
            T t = fetcher.apply(journal.getBizId());
            if (t == null) {
                throw new DataInvalidException("所关联的业务对象不存在");
            }
            if (!journal.getTraderId().equals(getTraderId(journal.getRevenueOrExpense(), journal.getTraderType(), t))) {
                throw new DataInvalidException("所关联的交易方不属于当前业务对象");
            }
            journal.setBizName(bizNameGetter.apply(t));
            journal.setBizNumber(bizNumberGetter.apply(t));
            if (bizSelfTypesGetter != null) {
                journal.setBizSelfTypes(bizSelfTypesGetter.apply(t));
            }
            if (projectIdGetter != null) {
                journal.setProjectId(projectIdGetter.apply(t));
            }
        }
    }
}
