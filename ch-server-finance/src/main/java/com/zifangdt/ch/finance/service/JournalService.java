package com.zifangdt.ch.finance.service;

import com.zifangdt.ch.base.api.*;
import com.zifangdt.ch.base.bo.*;
import com.zifangdt.ch.base.bo.cfg.finance.DetailOfJournalType;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.constant.NoticeTags;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.contract.Instalment;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.finance.Invoice;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.entity.Provider;
import com.zifangdt.ch.base.dto.product.entity.Purchase;
import com.zifangdt.ch.base.dto.ticket.output.TicketBrief;
import com.zifangdt.ch.base.dto.ticket.output.TicketDetailDto;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.enums.pair.*;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;
import com.zifangdt.ch.base.enums.ticket.MaintainType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;
import com.zifangdt.ch.base.web.UserVerboseFetcher;
import com.zifangdt.ch.finance.bo.*;
import com.zifangdt.ch.finance.mapper.InvoiceMapper;
import com.zifangdt.ch.finance.mapper.JournalMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/11.
 */
@Service
public class JournalService extends BaseService<Journal, Long> {
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private ConfigItemVerboseFetcher configItemVerboseFetcher;
    @Autowired
    private ConfigItemService configItemService;
    @Autowired
    private CustomerServerApi customerServerApi;
    @Autowired
    private UaaServerApi uaaServerApi;
    @Autowired
    private CommonServerApi commonServerApi;
    @Autowired
    private TicketServerApi ticketServerApi;
    @Autowired
    private ContractServerApi contractServerApi;
    @Autowired
    private UserVerboseFetcher userVerboseFetcher;
    @Autowired
    private InvoiceMapper invoiceMapper;

    private static final String TYPE_NAME_OF_CONTRACT_PERIOD = "合同分期回款";
    private static final String TYPE_NAME_OF_TICKET_REVENUE = "工单回款";
    private static final String TYPE_NAME_OF_TICKET_EXPENSE = "工单结算";
    private static final String TYPE_NAME_OF_CONTRACT_COMMISSION = "合同提成";
    private static final String TYPE_NAME_OF_PURCHASE = "采购支出";
    private static final String REVENUE_ADVANCE = "预付款";
    private static final String REVENUE_FULL = "一次性结清";

//    @PostConstruct
//    public void init() {
//        Map<String, Long> map = configItemService.details(ConfigType.journalTypes).stream()
//                .map(DetailOfOption.class::cast)
//                .collect(Collectors.toMap(DetailOfOption::getName, DetailOfOption::getId));
//        TYPE_OF_CONTRACT_PERIOD = map.get(TYPE_NAME_OF_CONTRACT_PERIOD);
//        TYPE_OF_TICKET_REVENUE = map.get(TYPE_NAME_OF_TICKET_REVENUE);
//        TYPE_OF_TICKET_EXPENSE = map.get(TYPE_NAME_OF_TICKET_EXPENSE);
//        TYPE_OF_CONTRACT_COMMISSION = map.get(TYPE_NAME_OF_CONTRACT_COMMISSION);
//        TYPE_OF_PURCHASE = map.get(TYPE_NAME_OF_PURCHASE);
//    }

    public PageResultBo<Journal> list(JournalListQuery query) {
        Map<Long, String> contractTypes = contractServerApi.contractTypes().stream().collect(Collectors.toMap(IdAndName::getId, IdAndName::getName));
//        Map<Long, String> ticketTypes = ticketServerApi.ticketTypes().stream().collect(Collectors.toMap(IdAndName::getId, IdAndName::getName));

        PageResultBo<Journal> bo = PageResultBo.of(query, journalMapper::getList, journal -> {
            if (!CollectionUtils.isEmpty(journal.getBizSelfTypes())) {
                if (journal.getBizType() == FinanceBizType.CONTRACT) {
                    journal.setBizSelfTypeNames(journal.getBizSelfTypes().stream().map(contractTypes::get).collect(Collectors.toList()));
                }
//                else if (journal.getBizType() == FinanceBizType.TICKET) {
//                    journal.setBizSelfTypeNames(journal.getBizSelfTypes().stream().map(ticketTypes::get).collect(Collectors.toList()));
//                }
            }

            if (journal.getAccountId().equals(Constants.TYPE_OF_ACCOUNT_TRANSFER)) {
                journal.setAccountName(Constants.TYPE_NAME_OF_ACCOUNT_TRANSFER);
            }
        });

        List<Journal> list = journalMapper.getListForSumMoney(query);
        BigDecimal pendingRevenue = BigDecimal.ZERO;
        BigDecimal pendingExpense = BigDecimal.ZERO;
        BigDecimal doneRevenue = BigDecimal.ZERO;
        BigDecimal doneExpense = BigDecimal.ZERO;
        for (Journal journal : list) {
            switch (journal.getStatus()) {
                case PENDING_REVENUE:
                    pendingRevenue = pendingRevenue.add(journal.getPlannedMoney());
                    break;
                case PENDING_EXPENSE:
                    pendingExpense = pendingExpense.add(journal.getPlannedMoney());
                    break;
                case DONE_REVENUE:
                    doneRevenue = doneRevenue.add(journal.getActualMoney());
                    break;
                case DONE_EXPENSE:
                    doneExpense = doneExpense.add(journal.getActualMoney());
                    break;
                case INVALID:
                    if (journal.getRevenueOrExpense() == RevenueOrExpense.REVENUE) {
                        doneRevenue = doneRevenue.add(journal.getActualMoney());
                    } else if (journal.getRevenueOrExpense() == RevenueOrExpense.EXPENSE) {
                        doneExpense = doneExpense.add(journal.getActualMoney());
                    }
            }
        }
        bo.setExtraInfo(new JournalStatistics(pendingRevenue, doneRevenue, pendingExpense, doneExpense));
        return bo;
    }

//    public List<JournalExcelExportBo> findListToExport(JournalListQuery queryBo) {
//        queryBo.setCurrent(1);
//        queryBo.setSize(Integer.MAX_VALUE);
//        List<Journal> list = list(queryBo).getRecords();
//        configItemVerboseFetcher.fetch(Stream.concat(list.stream().map(Journal::getType), list.stream().map(Journal::getAccount)).collect(Collectors.toList()));
//        return list.stream().map(this::mapToJournalExcelExportBo).collect(Collectors.toList());
//    }
//
//    private JournalExcelExportBo mapToJournalExcelExportBo(Journal journal) {
//        JournalExcelExportBo bo = new JournalExcelExportBo();
//        bo.setId(journal.getId());
//        bo.setTime(DateUtil.format(journal.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
//        bo.setTypeName(configItemVerboseFetcher.nameOf(journal.getType()));
//        bo.setAccountName(configItemVerboseFetcher.nameOf(journal.getAccount()));
//        bo.setRevenueOrExpense(journal.getRevenueOrExpense().getName());
//        bo.setMoney((journal.getRevenueOrExpense() == RevenueOrExpense.REVENUE ? "+" : "-") + journal.getMoney());
//        return bo;
//    }

    public Long saveJournal(AbstractJournalCreate bo) {
        RevenueOrExpense belongsTo = bo.getStatus().getBelongsTo();
        if (belongsTo == null || !bo.supportedStatuses().contains(bo.getStatus())) {
            throw new DataInvalidException("状态不正确");
        }
        DetailOfJournalType type = (DetailOfJournalType) configItemService.detail(bo.getType());
        if (belongsTo != type.getBelongsTo()) {
            throw new DataInvalidException("该账目类型不属于" + belongsTo.getName() + "类别");
        }
        Journal journal = new Journal();
        BeanUtils.copyProperties(bo, journal);
        journal.setRevenueOrExpense(belongsTo);

        journal.getTraderType().getTraderBehavior().checkTrader(journal);

        //如果没有关联业务
        if (journal.getBizType() == null || journal.getBizId() == null) {
            //预设类型必须关联业务
            if (type.getBizType() != null) {
                throw new DataInvalidException("该账目类型必须指定关联业务");
            }
        } else {
            //如果关联了业务
            //限定预设类型的关联业务
            if (type.getBizType() != null && type.getBizType() != journal.getBizType()) {
                throw new DataInvalidException("该账目类型只能关联" + type.getBizType().getName());
            }

            //限定交易方
            if (type.getTraderType() != null) {
                if (type.getTraderType() != journal.getTraderType()) {
                    throw new DataInvalidException("该账目类型支持的交易方只能是" + type.getTraderType().getName());
                }
            } else if (!journal.getBizType().getBizBehavior().supports(journal.getRevenueOrExpense(), journal.getTraderType())) {
                throw new DataInvalidException("该账目类型支持的交易方只能是" + journal.getBizType().getBizBehavior().supportedTraderTypeNames(journal.getRevenueOrExpense(), journal.getTraderType()));
            }

            if (type.getId().equals(Constants.TYPE_OF_CONTRACT_PERIOD)) {
                if (journal.getDetails().length > 1) {
                    throw new DataInvalidException("该账目类型只能指定一条明细数据");
                }
                int period = journalMapper.maxContractPeriod(journal.getBizId(), Constants.TYPE_OF_CONTRACT_PERIOD);
                journal.setContractPeriod(period);
            } else if (type.getId().equals(Constants.TYPE_OF_CONTRACT_COMMISSION)) {
                if (journal.getDetails().length > 1) {
                    throw new DataInvalidException("该账目类型只能指定一条明细数据");
                }
                int commission = journalMapper.maxContractCommission(journal.getBizId(), Constants.TYPE_OF_CONTRACT_COMMISSION);
                journal.setContractCommission(commission);
            }

            journal.getBizType().getBizBehavior().checkBiz(journal);

        }

        save(journal);
        return journal.getId();
    }

    @Override
    protected void preSave(Journal toBeSaved) {
        toBeSaved.setCreateName(CurrentUser.getName());
        if (toBeSaved.getStatus() == JournalStatus.DONE_REVENUE || toBeSaved.getStatus() == JournalStatus.DONE_EXPENSE) {
            toBeSaved.setOperateId(toBeSaved.getCreateId());
            toBeSaved.setOperateName(toBeSaved.getCreateName());
            toBeSaved.setOperateTime(toBeSaved.getCreateTime());
        }
        if (toBeSaved.getHandleId() != null) {
            if (toBeSaved.getHandleId().equals(toBeSaved.getCreateId())) {
                toBeSaved.setHandleName(toBeSaved.getCreateName());
            } else {
                User user = userVerboseFetcher.one(toBeSaved.getHandleId());
                if (user == null) {
                    throw new DataInvalidException("经办人不存在");
                }
                toBeSaved.setHandleName(user.getName());
            }
        }
    }

    public void noteContractPeriod(ContractPeriodBo bo) {
        Date now = new Date();
        List<Journal> journals = new ArrayList<>();
        NoticeType noticeType = null;
        Contract contract = bo.getContract();
        Customer customer = customerServerApi.getOneCustomer(contract.getCustomerId());
        Map<Long, String> optionNames = bo.getOptionNames();
        if (contract.getPayStyle() == PayStyle.INSTALMENT) {
            if (contract.getHasAdvance()) {
                journals.add(journalForContractPeriod(contract, customer, now, contract.getAdvance(), REVENUE_ADVANCE, null));
                noticeType = NoticeType.REVENUE_ADVANCE;
            }

            for (Instalment instalment : contract.getInstalments()) {
                String name = optionNames.get(instalment.getContractType()) + "-" + optionNames.get(instalment.getProcedure()) + "-" + instalment.getNode().getName();
                journals.add(journalForContractPeriod(contract, customer, null, instalment.getPlannedMoney(), name, instalment.getSerialNumber()));
            }

        } else {
            journals.add(journalForContractPeriod(contract, customer, now, contract.getFull(), REVENUE_FULL, null));
            noticeType = NoticeType.REVENUE_FULL;
        }
        journalMapper.insertList(journals);

        if (noticeType != null) {
            Set<Long> ids = configItemService.financialStaff();
            if (!CollectionUtils.isEmpty(ids)) {
                commonServerApi.saveNotice(
                        Notice.newBuilder()
                                .name(TYPE_NAME_OF_CONTRACT_PERIOD)
                                .type(noticeType, contract.getNumber())
                                .tag(NoticeTags.CONTRACT)
                                .redirect(RedirectType.JOURNAL_DETAIL, journals.get(0).getId())
                                .receivers(ids)
                                .build()
                );
            }
        }
    }

    private Journal journalForContractPeriod(
            Contract contract,
            Customer customer,
            Date plannedDate,
            BigDecimal plannedMoney,
            String name,
            Integer contractPeriod) {
        Journal journal = new Journal();
        journal.setPlannedMoney(plannedMoney);
        journal.setPlannedDate(plannedDate);
        journal.setContractPeriod(contractPeriod);
        journal.setDetails(new Journal.JournalDetail[]{new Journal.JournalDetail(name, plannedMoney)});
        setCommonPropertiesForContractPeriod(journal, contract, customer);
        return journal;
    }

    private void setCommonPropertiesForContractPeriod(
            Journal journal,
            Contract contract,
            Customer customer) {
        journal.setType(Constants.TYPE_OF_CONTRACT_PERIOD);
        journal.setStatus(JournalStatus.PENDING_REVENUE);
        journal.setBizType(FinanceBizType.CONTRACT);
        journal.setBizId(contract.getId());
        journal.setBizNumber(contract.getNumber());
        journal.setBizName(contract.getNumber());
        journal.setBizSelfTypes(contract.getTypes());
        journal.setTraderType(TraderType.CUSTOMER);
        journal.setTraderId(contract.getCustomerId());
        journal.setTraderInfo(customer.getName() + "," + customer.getPhone());
        journal.setHandleId(contract.getCreateId());
        journal.setCreateId(CurrentUser.getUserId());
        journal.setCreateTime(new Date());
        journal.setProjectId(contract.getProjectId());
    }

    public List<Journal> findContractPeriodRecords(Long contractId) {
        return journalMapper.findContractPeriodRecords(contractId, Constants.TYPE_OF_CONTRACT_PERIOD);
    }

    public void noteTicket(JournalTicketBo journalTicketBo) {
        TicketDetailDto ticket = journalTicketBo.getTicket();
        Journal journal = new Journal();
        if (journalTicketBo.getRevenueOrExpense() == RevenueOrExpense.REVENUE) {
            journal.setDetails(new Journal.JournalDetail[]{new Journal.JournalDetail(TYPE_NAME_OF_TICKET_REVENUE, journalTicketBo.getPlannedMoney())});
            journal.setType(Constants.TYPE_OF_TICKET_REVENUE);
            journal.setStatus(JournalStatus.PENDING_REVENUE);
            journal.setTraderType(TraderType.CUSTOMER);
            journal.setTraderId(ticket.getCustomerId());
            Customer customer = customerServerApi.getOneCustomer(ticket.getCustomerId());
            journal.setTraderInfo(customer.getName() + "," + customer.getPhone());
        } else if (journalTicketBo.getRevenueOrExpense() == RevenueOrExpense.EXPENSE) {
            journal.setDetails(new Journal.JournalDetail[]{new Journal.JournalDetail(TYPE_NAME_OF_TICKET_EXPENSE, journalTicketBo.getPlannedMoney())});
            journal.setType(Constants.TYPE_OF_TICKET_EXPENSE);
            journal.setStatus(JournalStatus.PENDING_EXPENSE);
            journal.setTraderType(TraderType.STAFF);
            journal.setTraderId(ticket.getOperatorId());
            User user = userVerboseFetcher.one(ticket.getOperatorId());
            journal.setTraderInfo(user.getName() + "," + user.getPhone());
        }
        journal.setPlannedMoney(journalTicketBo.getPlannedMoney());
        journal.setPlannedDate(journalTicketBo.getPlannedDate());

        journal.setBizType(FinanceBizType.TICKET);
        journal.setBizId(ticket.getId());
        journal.setBizNumber(ticket.getSerialNumber());
        journal.setBizName(ticket.getSerialNumber());
        journal.setBizSelfTypes(Collections.singletonList(ticket.getTicketTypeId()));

        journal.setHandleId(ticket.getCreateId());
        journal.setCreateId(CurrentUser.getUserId());
        journal.setCreateTime(new Date());
        journal.setProjectId(ticket.getProjectId());
        save(journal);
    }

    public void notePurchase(JournalPurchaseBo journalPurchaseBo) {
        Purchase purchase = journalPurchaseBo.getPurchase();
        Journal journal = new Journal();

        journal.setDetails(new Journal.JournalDetail[]{new Journal.JournalDetail(TYPE_NAME_OF_PURCHASE, journalPurchaseBo.getPlannedMoney())});
        journal.setType(Constants.TYPE_OF_PURCHASE);
        journal.setStatus(JournalStatus.PENDING_EXPENSE);
        journal.setTraderType(TraderType.PROVIDER);
        journal.setTraderId(purchase.getProviderId());
        Provider provider = null;
        journal.setTraderInfo(provider.getName() + "," + provider.getPhone());
        journal.setPlannedMoney(journalPurchaseBo.getPlannedMoney());
        journal.setPlannedDate(journalPurchaseBo.getPlannedDate());

        journal.setBizType(FinanceBizType.PURCHASE);
        journal.setBizId(purchase.getId());
        journal.setBizNumber(purchase.getNum());
        journal.setBizName(purchase.getName());

        journal.setHandleId(purchase.getCreateId());
        journal.setCreateId(CurrentUser.getUserId());
        journal.setCreateTime(new Date());
        save(journal);
    }

    public void updatePlannedTimeForInstalment(Long contractId, String contractTypeName, String procedureName, RefundNoticeStep step) {
        journalMapper.updatePlannedTimeForInstalment(contractId, Constants.TYPE_OF_CONTRACT_PERIOD, contractTypeName + "-" + procedureName + "-" + ProcedureNode.from(step).getName());
    }

    private List<InventoryClearingBo> generateClearingBos(List<Journal> clearingExpenses) {
        List<InventoryClearingBo> clearingBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(clearingExpenses)) {
            Map<Long, Journal> map = clearingExpenses.stream().collect(Collectors.toMap(Journal::getBizId, Function.identity()));
            List<TicketBrief> tickets = ticketServerApi.getMultiTicketForProject(map.keySet());
            tickets.forEach(t -> t.setExpenseId(map.get(t.getId()).getId()));

            Map<Long, List<TicketBrief>> group = tickets.stream()
                    .filter(t -> t.getMaintainType() == MaintainType.WARRANTY && t.getContractTypeId() != null)
                    .collect(Collectors.groupingBy(TicketBrief::getContractTypeId));
            Set<Long> contractTypes = new HashSet<>();
            for (Map.Entry<Long, List<TicketBrief>> e : group.entrySet()) {
                clearingBos.add(new InventoryClearingBo(e.getKey(), e.getValue()));
                contractTypes.add(e.getKey());
            }
            if (!CollectionUtils.isEmpty(contractTypes)) {
                Map<Long, String> typeNames = contractServerApi.optionNames(contractTypes);
                for (InventoryClearingBo bo : clearingBos) {
                    bo.setContractTypeName(typeNames.get(bo.getContractType()));
                }
            }
        }
        return clearingBos;
    }

    private Map<String, Object> generateClearingBosAndTickets(List<Journal> clearingExpenses) {
        List<InventoryClearingBo> clearingBos = new ArrayList<>();
        List<InventoryTicketBo> maintainInTicketBos = new ArrayList<>();
        List<InventoryTicketBo> maintainOutTicketBos = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(clearingExpenses)) {
            Map<Long, Journal> map = clearingExpenses.stream().collect(Collectors.toMap(Journal::getBizId, Function.identity()));
            List<TicketBrief> tickets = ticketServerApi.getMultiTicketForProject(map.keySet());
            tickets.forEach(t -> t.setExpenseId(map.get(t.getId()).getId()));

            Map<Boolean, List<TicketBrief>> maintainMap = tickets.stream().collect(Collectors.partitioningBy(t -> t.getMaintainType() == MaintainType.WARRANTY));
            Map<Boolean, List<TicketBrief>> contractTypeMap = maintainMap.get(true).stream().collect(Collectors.partitioningBy(t -> t.getContractTypeId() != null));
            maintainInTicketBos.addAll(contractTypeMap.get(false).stream().map(InventoryTicketBo::new).collect(Collectors.toList()));
            maintainOutTicketBos.addAll(maintainMap.get(false).stream().map(InventoryTicketBo::new).collect(Collectors.toList()));

            Map<Long, List<TicketBrief>> group = contractTypeMap.get(true).stream()
                    .collect(Collectors.groupingBy(TicketBrief::getContractTypeId));
            Set<Long> contractTypes = new HashSet<>();
            for (Map.Entry<Long, List<TicketBrief>> e : group.entrySet()) {
                clearingBos.add(new InventoryClearingBo(e.getKey(), e.getValue()));
                contractTypes.add(e.getKey());
            }
            if (!CollectionUtils.isEmpty(contractTypes)) {
                Map<Long, String> typeNames = contractServerApi.optionNames(contractTypes);
                for (InventoryClearingBo bo : clearingBos) {
                    bo.setContractTypeName(typeNames.get(bo.getContractType()));
                }
            }
        }
        result.put("maintainInTickets", maintainInTicketBos);
        result.put("maintainOutTickets", maintainOutTicketBos);
        result.put("constructionClearing", clearingBos);
        return result;
    }


    public List<InventoryClearingBo> clearingBos(Long projectId) {
        List<Journal> expenses = journalMapper.listForInventoryByType(projectId, Constants.TYPE_OF_TICKET_EXPENSE);
        return generateClearingBos(expenses);
    }

    public Map<String, Object> journalsForInventory(Long projectId) {
        Map<String, Object> result = new HashMap<>();
        List<Journal> journals = journalMapper.listForInventory(projectId);
        Map<Boolean, List<Journal>> map = journals.stream().collect(Collectors.partitioningBy(e -> e.getRevenueOrExpense() == RevenueOrExpense.REVENUE));
        result.put("revenue", map.get(true));

        Map<Boolean, List<Journal>> partitioned = map.get(false).stream().collect(Collectors.partitioningBy(e -> e.getType().equals(Constants.TYPE_OF_TICKET_EXPENSE)));
        result.put("expense", partitioned.get(false));
        result.putAll(generateClearingBosAndTickets(partitioned.get(true)));
        return result;
    }

    public List<Journal> relatedRecords(FinanceBizType bizType, Long bizId) {
        return journalMapper.relatedRecords(bizType.getIntVerifier(), bizId);
    }

    public BigDecimal sumDebtsOfOthers(TraderType traderType, Long traderId) {
        return journalMapper.sumDebts(JournalStatus.PENDING_REVENUE.getIntVerifier(), traderType.getIntVerifier(), traderId);
    }

    public BigDecimal sumDebtsOfMyself(TraderType traderType, Long traderId) {
        return journalMapper.sumDebts(JournalStatus.PENDING_EXPENSE.getIntVerifier(), traderType.getIntVerifier(), traderId);
    }

    public Journal getDetail(Long id) {
        Journal detail = journalMapper.getDetail(id);
        if (detail == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        if (detail.getType().equals(Constants.TYPE_OF_CONTRACT_PERIOD)) {
            detail.setExtraDetails(journalMapper.findContractPeriodRecords(detail.getBizId(), Constants.TYPE_OF_CONTRACT_PERIOD));
        } else if (detail.getType().equals(Constants.TYPE_OF_CONTRACT_COMMISSION)) {
            detail.setExtraDetails(journalMapper.findContractCommissionRecords(detail.getBizId(), Constants.TYPE_OF_CONTRACT_COMMISSION));
        }
        if (detail.getRevenueOrExpense() == RevenueOrExpense.REVENUE) {
            detail.setDebts(sumDebtsOfOthers(detail.getTraderType(), detail.getTraderId()));
        } else if (detail.getRevenueOrExpense() == RevenueOrExpense.EXPENSE) {
            detail.setDebts(sumDebtsOfMyself(detail.getTraderType(), detail.getTraderId()));
        }

        return detail;
    }

    public void invalid(Long id) {
        Journal journal = get(id);
        if (journal == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        if (journal.getStatus() != JournalStatus.DONE_REVENUE && journal.getStatus() != JournalStatus.DONE_EXPENSE) {
            throw new WrongOperationException("只能失效已回款或者已支付的记录");
        }
        Journal update = new Journal();
        update.setStatus(JournalStatus.INVALID);
        update.setInvalidTime(new Date());
        update.setInvalidId(CurrentUser.getUserId());
        update.setInvalidName(CurrentUser.getName());
        updateInternal(update);
    }

    public void delete(Long id) {
        Journal journal = get(id);
        if (journal == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        if (journal.getStatus() != JournalStatus.PENDING_REVENUE && journal.getStatus() != JournalStatus.PENDING_EXPENSE) {
            throw new WrongOperationException("只能删除待回款或者待支付的记录");
        }
        Journal update = new Journal();
        update.setStatus(JournalStatus.DELETED);
        update.setOperateTime(new Date());
        update.setOperateId(CurrentUser.getUserId());
        update.setOperateName(CurrentUser.getName());
        updateInternal(update);
    }

    public void updateDone(Long id, JournalUpdateDone bo) {
        Journal journal = get(id);
        if (journal == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        Journal update = new Journal();
        BeanUtils.copyProperties(bo, update);
        update.setOperateTime(new Date());
        update.setOperateId(CurrentUser.getUserId());
        update.setOperateName(CurrentUser.getName());

        if (journal.getStatus() == JournalStatus.PENDING_REVENUE) {
            update.setStatus(JournalStatus.DONE_REVENUE);
        } else if (journal.getStatus() == JournalStatus.PENDING_EXPENSE) {
            update.setStatus(JournalStatus.DONE_EXPENSE);
        } else {
            throw new WrongOperationException("只能处理待回款或者待支付的记录");
        }

        updateInternal(update);
    }

    public void addInvoice(Long id, Invoice bo) {
        Journal journal = get(id);
        if (journal == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        if (journal.getStatus() != JournalStatus.DONE_REVENUE) {
            throw new WrongOperationException("只能对已回款的记录记开票");
        }
        if (journalMapper.getInvoiceId(id) != null) {
            throw new WrongOperationException("该记录已经开过发票");
        }
        bo.setJournalId(id);
        bo.setCreateId(CurrentUser.getUserId());
        bo.setCreateTime(new Date());
        invoiceMapper.insertSelective(bo);
    }

    public void updateInvoice(Long id, Invoice bo) {
        Journal journal = get(id);
        if (journal == null) {
            throw new DataNotFoundException("该记录不存在");
        }
        Long invoiceId = journalMapper.getInvoiceId(id);
        if (invoiceId == null) {
            throw new WrongOperationException("该记录尚未记开票");
        }
        bo.setJournalId(null);
        bo.setId(invoiceId);
        bo.setModifyId(CurrentUser.getUserId());
        bo.setModifyTime(new Date());
        invoiceMapper.updateByPrimaryKeySelective(bo);
    }

    public void batchUpdateDone(JournalBatchUpdateDone bo) {
        if (bo.getIds().size() != bo.getActualMoneys().size()) {
            throw new DataInvalidException("ids和actualMoneys中的元素数量必须一致");
        }
        List<Journal> list = journalMapper.getByIdsForBulkUpdate(bo.getIds());
        if (list.size() != bo.getIds().size()) {
            throw new DataInvalidException("有部分记录不存在");
        }
        JournalStatus status = list.get(0).getStatus();
        if (list.stream().skip(1).anyMatch(journal -> journal.getStatus() != status)) {
            throw new DataInvalidException("批量处理的所有记录的状态必须一致");
        }
        if (status == JournalStatus.PENDING_REVENUE) {
            bo.setStatus(JournalStatus.DONE_REVENUE);
        } else if (status == JournalStatus.PENDING_EXPENSE) {
            bo.setStatus(JournalStatus.DONE_EXPENSE);
        } else {
            throw new WrongOperationException("只能处理待回款或者待支付的记录");
        }
        bo.setCurrentUserId(CurrentUser.getUserId());
        bo.setCurrentUserName(CurrentUser.getName());
        journalMapper.batchUpdateDone(bo);
    }

    public boolean existsAdjustRecord(Long accountId) {
        return journalMapper.existsAdjustRecord(accountId, Constants.TYPE_OF_ADJUST);
    }
}
