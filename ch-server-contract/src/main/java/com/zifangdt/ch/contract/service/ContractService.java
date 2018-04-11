package com.zifangdt.ch.contract.service;

import com.zifangdt.ch.base.api.*;
import com.zifangdt.ch.base.bo.*;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoiceWithOwner;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfContractInvalid;
import com.zifangdt.ch.base.constant.NewsTags;
import com.zifangdt.ch.base.constant.NoticeTags;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.contract.Instalment;
import com.zifangdt.ch.base.dto.contract.ProductDetail;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.projectmanager.ProjectResult;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.enums.pair.*;
import com.zifangdt.ch.base.enums.project.RefundNoticeStep;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.NotPermittedException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.util.WebUtil;
import com.zifangdt.ch.base.web.BillVerboseFetcher;
import com.zifangdt.ch.base.web.ConfigItemVerboseFetcher;
import com.zifangdt.ch.base.web.CustomerVerboseFetcher;
import com.zifangdt.ch.base.web.UserVerboseFetcher;
import com.zifangdt.ch.contract.bo.*;
import com.zifangdt.ch.contract.mapper.*;
import com.zifangdt.ch.contract.service.cfg.ConfigItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Service
public class ContractService extends BaseService<Contract, Long> {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private InstalmentMapper instalmentMapper;
    @Autowired
    private ConfigItemService configItemService;
    @Autowired
    private ApprovalServerApi approvalServerApi;
    @Autowired
    private UserVerboseFetcher userVerboseFetcher;
    @Autowired
    private UaaServerApi uaaServerApi;
    @Autowired
    private ConfigItemVerboseFetcher configItemVerboseFetcher;
    @Autowired
    private FinanceServerApi financeServerApi;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Autowired
    private Accounting1Mapper accounting1Mapper;
    @Autowired
    private Accounting2Mapper accounting2Mapper;
    @Autowired
    private CommonServerApi commonServerApi;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CustomerVerboseFetcher customerVerboseFetcher;
    @Autowired
    private CustomerServerApi customerServerApi;
    @Autowired
    private ManagerServerApi managerServerApi;
    @Autowired
    private BillVerboseFetcher billVerboseFetcher;
    @Autowired
    private ProductServerApi productServerApi;

    private void checkFinance(Contract contract) {
        checkFinance(contract, contract.getTypes());
    }

    private void checkFinance(Contract contract, List<Long> types) {
        if (contract.getPayStyle() == PayStyle.INSTALMENT) {
            if (contract.getHasAdvance() == null) {
                throw new DataInvalidException("[hasAdvance]分期时必须指定是否有预付款");
            }
            if (contract.getHasAdvance()) {
                if (contract.getAdvance() == null) {
                    throw new DataInvalidException("[advance]hasAdvance为true时必须指定预付款");
                }
            } else {
                contract.setAdvance(null);
            }
            if (contract.getInstalments() == null || contract.getInstalments().size() < 2) {
                throw new DataInvalidException("[instalments]回款期数必须至少是2期");
            }

            if (contract.getInstalments().stream().anyMatch(n -> !types.contains(n.getContractType()))) {
                throw new DataInvalidException("[instalments.contractType]回款环节选择的工期必须在合同类型范围之内");
            }
            if (contract.getInstalments().stream().anyMatch(n -> {
                List<Long> procedures = configItemService.findProcedures(n.getContractType()).stream().map(IdAndName::getId).collect(Collectors.toList());
                return !procedures.contains(n.getProcedure());
            })) {
                throw new DataInvalidException("[instalments.procedure]回款环节选择的工序必须在工期关联的工序范围之内");
            }

            contract.setFull(null);
//            BigDecimal total = toBeSaved.getInstalments().stream().reduce(BigDecimal.ZERO, (money, instalment) -> money.add(instalment.getPlannedMoney()), BigDecimal::add);
//            if (toBeSaved.getAdvance() != null) {
//                total = total.add(toBeSaved.getAdvance());
//            }
//            if (!total.equals(toBeSaved.getMoney())) {
//                throw new DataInvalidException(String.format("总计划回款金额(%s)与合同总金额(%s)不一致", total, toBeSaved.getMoney()));
//            }
        } else {
            if (contract.getFull() == null) {
                throw new DataInvalidException("[full]没有指定一次性结清款");
            }
            contract.setHasAdvance(false);
            contract.setAdvance(null);
            contract.setInstalments(null);
        }
    }

    @Override
    protected void preSave(Contract toBeSaved) {
        checkFinance(toBeSaved);

        toBeSaved.setOwner(CurrentUser.getUserId());
        toBeSaved.setOwnerName(CurrentUser.getName());

        Customer customer = customerVerboseFetcher.one(toBeSaved.getCustomerId());
        if (customer == null || customer.getStatus() == CustomerStatus.INVALID || !customer.getOwner().equals(CurrentUser.getUserId())) {
            throw new WrongOperationException("请选择正确的客户");
        }
        toBeSaved.setCustomerSourceRelationId(customer.getSourceRelationId());
        toBeSaved.setCustomerName(customer.getName());
        if (!CollectionUtils.isEmpty(toBeSaved.getBills())) {
            toBeSaved.setBillNames(billVerboseFetcher.namesOf(toBeSaved.getBills()));
        }
    }

    private void saveInstalments(Long contractId, List<Instalment> list) {
        for (int i = 0, size = list.size(); i < size; i++) {
            Instalment instalment = list.get(i);
            instalment.setId(null);
            instalment.setContractId(contractId);
            instalment.setSerialNumber(i + 1);
        }
        instalmentMapper.insertList(list);
    }

    @Override
    protected void postSave(Contract saved) {
        if (saved.getInstalments() != null) {
            saveInstalments(saved.getId(), saved.getInstalments());
        }

        //发起流程
        Long processId = approvalServerApi.launchProcess(saved.getPrintNumber(), ProcessType.CONTRACT, saved.getId());

        Contract update = new Contract();
        update.setId(saved.getId());
        update.setProcessId(processId);
        contractMapper.updateByPrimaryKeySelective(update);

        newsService.save(
                News.newBuilder()
                        .operator(CurrentUser.getUserId())
                        .tag(NewsTags.ACTION)
                        .type(NewsType.CONTRACT_CREATED, CurrentUser.getName(), saved.getPrintNumber())
                        .relatedId(saved.getId())
                        .happenedAt(saved.getCreateTime())
                        .build()
        );

        customerServerApi.callbackForContract(saved.getCustomerId(), saved.getId(), true);
    }

    public Contract getDetailFromProcessModule(Long contractId, ProcessOverview overview) {
        Contract contract = contractMapper.getDetail(contractId);
        contract.setProcessOverview(overview);
        extraInfo(contract);
        return contract;
    }

    private void extraInfo(Contract contract) {
        if (contract.getProcessOverview().getProcess().getStatus() == ProcessStatus.APPROVED) {
            List<Journal> revenues = financeServerApi.periodRecords(contract.getId());
            contract.setPeriodRecords(revenues);
        }

        if (com.zifangdt.ch.base.constant.Constants.ADMIN_USER_ID.equals(CurrentUser.getUserId())) {
            contract.setPermittedToModify(true);
            contract.setPermittedToInvalid(true);
        } else {
            contract.setPermittedToModify(contract.getOwner().equals(CurrentUser.getUserId()));
            DetailOfUserChoiceWithOwner perms = (DetailOfUserChoiceWithOwner) configItemService.perms(ConfigType.contractInvalidPermission);
            if (perms.getOwnerIncluded()) {
                perms.getUsers().add(contract.getOwner());
            }
            contract.setPermittedToInvalid(perms.getUsers().contains(CurrentUser.getUserId()));
        }
        contract.setPermittedToAddRevenueRecord(financeServerApi.financialStaff().contains(CurrentUser.getUserId()));

        Map<String, Object> customerJson = customerServerApi.detailJson(contract.getCustomerId());
        contract.setCustomerDetail(customerJson);

        if (contract.getOwner() != null) {
            contract.setOwnerOrganization(userVerboseFetcher.one(contract.getOwner()).getOrganizationName());
        }

        if (contract.getCustomerSourceRelationId() != null) {
            contract.setCustomerSourceRelationName(customerServerApi.sourceRelationName(contract.getCustomerId(), contract.getCustomerSourceRelationId()));
        }
    }

    @Override
    protected void postFindDetail(Contract contract) {
        if (!WebUtil.isFeignRequest()) {
            if (!CurrentUser.permittedForContractData() && !CurrentUser.getUserId().equals(contract.getOwner())) {
                throw new WrongOperationException("无权访问其他用户拥有的合同");
            }
        }

        ProcessOverview overview = approvalServerApi.overview(contract.getProcessId());
        contract.setProcessOverview(overview);

        extraInfo(contract);

    }

    public Contract contractWithCustomer(Long id) {
        List<Contract> details = getDetails(new HashSet<>(Collections.singletonList(id)));
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        Contract contract = details.get(0);
        Map<String, Object> customerJson = customerServerApi.detailJson(contract.getCustomerId());
        contract.setCustomerDetail(customerJson);
        return contract;
    }

    public List<Contract> getDetails(Set<Long> ids) {
        return contractMapper.getDetails(new ArrayList<>(ids));
    }

    @Override
    protected Set<Long> generateOwners(int ownerType) {
        if (CurrentUser.permittedForContractData() && ownerType == 0) {
            return null;
        }
        return super.generateOwners(ownerType);
    }

    public PageResultBo<Contract> findListForWeb(QueryBo queryBo) {
        Set<Long> owners = generateOwners(queryBo.getOwnerType());
        queryBo.setOwners(owners == null ? null : new ArrayList<>(owners));
        return PageResultBo.of(queryBo, contractMapper::findList);
    }

    private void invalidInternal(Contract contract, String reason, Boolean closeProject) {
        ProcessInstance processInstance = approvalServerApi.getProcessDetail(contract.getProcessId());
        if (processInstance.getStatus() == ProcessStatus.CANCELED) {
            throw new WrongOperationException(String.format("该合同(id=%s)已经失效", contract.getId()));
        }

        approvalServerApi.setStatusToCanceled(contract.getProcessId());

        Contract update = new Contract();
        update.setInvalidReason(reason);
        update.setInvalidated(true);
        update.setId(contract.getId());
        updateInternal(update);

        if (closeProject && contract.getProjectId() != null) {
            ProjectResult projectResult = new ProjectResult();
            projectResult.setProjectId(contract.getProjectId());
            managerServerApi.closeProject(projectResult);
        }

        newsService.save(
                News.newBuilder()
                        .operator(CurrentUser.getUserId())
                        .tag(NewsTags.ACTION)
                        .type(NewsType.CONTRACT_INVALID, CurrentUser.getName(), contract.getPrintNumber())
                        .relatedId(contract.getId())
                        .happenedAt(update.getModifyTime())
                        .build()
        );

        customerServerApi.callbackForContract(contract.getCustomerId(), contract.getId(), false);
    }

    public void invalid(Long id, String reason) {
        Contract c = get(id);
        if (c == null) {
            throw new DataNotFoundException();
        }
        if (c.getInvalidated()) {
            throw new WrongOperationException("该合同已失效");
        }
        DetailOfContractInvalid perms = (DetailOfContractInvalid) configItemService.perms(ConfigType.contractInvalidPermission);
        Set<Long> ids = perms.getUsers();
        if (perms.getOwnerIncluded()) {
            ids.add(c.getOwner());
        }
        if (!ids.contains(CurrentUser.getUserId())) {
            throw new NotPermittedException("没有将合同设为失效的权限");
        }
        invalidInternal(c, reason, perms.getCloseProjectMeanwhile());
    }

    public void invalidBatch(List<Long> ids, String reason) {
        DetailOfContractInvalid perms = (DetailOfContractInvalid) configItemService.perms(ConfigType.contractInvalidPermission);
        Set<Long> permsUsers = perms.getUsers();
        for (Long id : ids) {
            Contract c = get(id);
            if (c == null) {
                throw new DataNotFoundException();
            }
            if (c.getInvalidated()) {
                throw new WrongOperationException("该合同已失效");
            }
            if (perms.getOwnerIncluded()) {
                permsUsers.add(c.getOwner());
            }
            if (!permsUsers.contains(CurrentUser.getUserId())) {
                throw new NotPermittedException("没有将合同设为失效的权限");
            }
            invalidInternal(c, reason, perms.getCloseProjectMeanwhile());
        }
//        approvalServerApi.setStatusToCanceledBatch(ids);
//        contractMapper.setInvalidReasonBatch(ids, reason);
    }

    @Override
    protected void preUpdate(Contract stored, Contract toBeUpdated, Class<?> updateBoClass) {
        if (stored.getInvalidated()) {
            throw new RuntimeException("该合同已失效");
        }
        if (updateBoClass == CreateBo.class) {
            if (!stored.getOwner().equals(CurrentUser.getUserId())) {
                throw new NotPermittedException("你不是该合同的所有人");
            }
            ProcessOverview overview = approvalServerApi.overview(stored.getProcessId());
            if (overview.getProcess().getStatus() != ProcessStatus.PENDING && overview.getCurrentStepIndex() != 0) {
                throw new NotPermittedException("只有待审批的合同才可以编辑基本信息");
            }
            checkFinance(toBeUpdated, stored.getTypes());

            if (!CollectionUtils.isEmpty(toBeUpdated.getBills()) && !StringUtils.join(stored.getBills(), ",").equals(StringUtils.join(toBeUpdated.getBills(), ","))) {
                toBeUpdated.setBillNames(billVerboseFetcher.namesOf(toBeUpdated.getBills()));
            }
        } else if (updateBoClass == ApproveFinanceBo.class) {
            checkBeforeApprove(stored);
            checkFinance(toBeUpdated, stored.getTypes());
            if (!toBeUpdated.getNumber().equals(stored.getNumber()) && contractMapper.numberExists(toBeUpdated.getNumber())) {
                throw new DataInvalidException("[number]合同编号已存在");
            }
            if (toBeUpdated.getAccounting1().getHasGift() && toBeUpdated.getAccounting1().getGiftCost() == null) {
                throw new DataInvalidException("[giftCost]hasGift为true时必须指定赠品成本");
            }
        }
    }

    public void callbackAfterApproved(ProcessInstance processInstance) {
        Contract contract = getDetail(processInstance.getObject());
        ContractPeriodBo bo = new ContractPeriodBo();
        if (contract.getPayStyle() == PayStyle.INSTALMENT) {
            Set<Long> optionIds = new HashSet<>();
            for (Instalment instalment : contract.getInstalments()) {
                optionIds.add(instalment.getContractType());
                optionIds.add(instalment.getProcedure());
            }
            Map<Long, String> map = configItemVerboseFetcher.fetch(optionIds).stream()
                    .collect(Collectors.toMap(ConfigItem::getId, n -> ((DetailOfOption) n.getDetail()).getName()));

            bo.setOptionNames(map);
        }
        bo.setContract(contract);
        financeServerApi.contractPeriod(bo);

        //将客户的状态设为“签约客户”
        customerServerApi.callbackForApprovedContract(contract.getCustomerId());

        //创建项目并更新合同
        createProject(contract);
    }

    private void createProject(Contract contract) {
        ProjectInfoBo projectInfoBo = new ProjectInfoBo();
        projectInfoBo.setContractId(contract.getId());
        projectInfoBo.setContractName(contract.getPrintNumber());
        projectInfoBo.setContractNum(contract.getNumber());
        projectInfoBo.setContractMoney(contract.getMoney().doubleValue());
        Customer customer = customerVerboseFetcher.one(contract.getCustomerId());
        projectInfoBo.setCustomer(customer.getName());
        projectInfoBo.setCustomerId(customer.getId());
        projectInfoBo.setCustomerTel(customer.getPhone());
        projectInfoBo.setProjectAddress(customer.getAddress());
        projectInfoBo.setOwnerId(contract.getOwner());
        projectInfoBo.setOwner(contract.getOwnerName());

        Map<Long, String> contractTypeMap = configItemService.optionsWithDisabled(ConfigType.contractTypes).stream().collect(Collectors.toMap(IdAndName::getId, IdAndName::getName));
        List<ContractTypeBo> contractTypes = new ArrayList<>();
        Map<String, List<ProcedureNode>> nodeMap = contract.getInstalments().stream().collect(Collectors.groupingBy(n -> n.getContractType() + "#" + n.getProcedure(), Collectors.mapping(Instalment::getNode, Collectors.toList())));
        for (Long contractType : contract.getTypes()) {
            ContractTypeBo contractTypeBo = new ContractTypeBo();
            contractTypeBo.setTypeId(contractType);
            contractTypeBo.setTypeName(contractTypeMap.get(contractType));
            List<ContractTypeUnitBo> unitBos = new ArrayList<>();
            for (IdAndName idAndName : configItemService.findProcedures(contractType)) {
                ContractTypeUnitBo contractTypeUnitBo = new ContractTypeUnitBo();
                contractTypeUnitBo.setTypeUnitId(idAndName.getId());
                contractTypeUnitBo.setTypeUnitName(idAndName.getName());
                contractTypeUnitBo.setRefundNoticeSteps(new ArrayList<>());
                List<ProcedureNode> nodes = nodeMap.get(contractType + "#" + idAndName.getId());
                if (!CollectionUtils.isEmpty(nodes)) {
//                    contractTypeUnitBo.getRefundNoticeSteps().addAll(nodes.stream().map(RefundNoticeStep::from).collect(Collectors.toList()));
                    contractTypeUnitBo.setRefundNoticeSteps(nodes.stream().map(pn -> {
                        return RefundNoticeStep.from(pn).toString();
                    }).collect(Collectors.toList()));
                }
                unitBos.add(contractTypeUnitBo);
            }
            contractTypeBo.setTypeUnitBos(unitBos);
            contractTypes.add(contractTypeBo);
        }

        projectInfoBo.setContractTypes(contractTypes);
        Long projectId = managerServerApi.createProject(projectInfoBo);

        Contract update = new Contract();
        update.setProjectId(projectId);
        update.setId(contract.getId());
        contractMapper.updateByPrimaryKeySelective(update);
    }

    public void saveOrUpdateProductDetail(Long id, ProductDetail productDetail) {
        Contract contract = getDetail(id);
        if (contract == null) {
            throw new DataNotFoundException();
        }
        if (contract.getInvalidated()) {
            throw new RuntimeException("该合同已失效");
        }
        checkBeforeApprove(contract);
        productDetail.setContractId(id);
        if (contract.getProductDetail() == null) {
            productDetailMapper.insertSelective(productDetail);
        } else {
            productDetailMapper.updateByPrimaryKeySelective(productDetail);
        }
    }

    private void checkBeforeApprove(Contract contract) {
        ProcessInstance process = approvalServerApi.getProcessDetail(contract.getProcessId());
        if (process.getStatus() != ProcessStatus.APPROVING) {
            throw new NotPermittedException("只有审批中的合同才可以编辑审批信息");
        }
        if (!process.getCurrentApprovers().contains(CurrentUser.getUserId())) {
            throw new NotPermittedException("只有当前步骤的审核人员才可以编辑审批信息");
        }
    }

    @Override
    protected void postUpdate(Contract stored, Contract updated, Class<?> updateBoClass) {
        if (stored.getInstalments() != null) {
            instalmentMapper.deleteByContractId(stored.getId());
        }
        if (updated.getInstalments() != null) {
            saveInstalments(stored.getId(), updated.getInstalments());
        }
        if (updateBoClass == ApproveFinanceBo.class) {
            updated.getAccounting1().setContractId(stored.getId());
            updated.getAccounting2().setContractId(stored.getId());
            if (stored.getAccounting1() == null) {
                accounting1Mapper.insertSelective(updated.getAccounting1());
            } else {
                accounting1Mapper.updateByPrimaryKeySelective(updated.getAccounting1());
            }

            if (stored.getAccounting2() == null) {
                accounting2Mapper.insertSelective(updated.getAccounting2());
            } else {
                accounting2Mapper.updateByPrimaryKeySelective(updated.getAccounting2());
            }
        }

        newsService.save(
                News.newBuilder()
                        .operator(CurrentUser.getUserId())
                        .tag(NewsTags.ACTION)
                        .type(NewsType.CONTRACT_EDITED, CurrentUser.getName(), StringUtils.defaultIfEmpty(updated.getPrintNumber(), stored.getPrintNumber()))
                        .relatedId(stored.getId())
                        .happenedAt(updated.getModifyTime())
                        .build()
        );
    }

    public Contract check(Long id) {
        Contract c = get(id);
        if (c == null) {
            throw new DataNotFoundException();
        }
        if (c.getInvalidated()) {
            throw new RuntimeException("该合同已失效");
        }
        if (!c.getOwner().equals(CurrentUser.getUserId())) {
            throw new NotPermittedException("你不是该合同的所有人");
        }
        return c;
    }

    public void changeOwner(Long id, Long userId) {
        Contract c = check(id);
        if (!c.getOwner().equals(userId)) {
            Contract contract = new Contract();
            contract.setId(id);
            contract.setOwner(userId);
            contract.setOwnerName(userVerboseFetcher.one(userId).getName());
            updateInternal(contract);

            commonServerApi.saveNotice(
                    Notice.newBuilder()
                            .name(c.getPrintNumber())
                            .type(NoticeType.CONTRACT_TRANSFERRED, CurrentUser.getName(), c.getPrintNumber())
                            .tag(NoticeTags.CONTRACT)
                            .redirect(RedirectType.CONTRACT_DETAIL, id)
                            .receivers(configItemService.permittedUsers(ConfigType.contractNoticePermission))
                            .receivers(userId)
                            .build()
            );

            newsService.save(
                    News.newBuilder()
                            .operator(CurrentUser.getUserId())
                            .tag(NewsTags.ACTION)
                            .type(NewsType.CONTRACT_TRANSFERRED, CurrentUser.getName(), c.getPrintNumber(), contract.getOwnerName())
                            .relatedId(id)
                            .happenedAt(contract.getModifyTime())
                            .build()
            );
        }
    }

    public PageResultBo<Contract> findApproved(ApprovedContractQueryBo queryBo) {
        return PageResultBo.of(queryBo, contractMapper::findApproved);
    }

    public List<Product> products(Long id) {
        Contract c = contractMapper.getDetail(id);
        if (c == null) {
            throw new DataNotFoundException();
        }
        if (c.getProductDetail().getProducts() != null && c.getProductDetail().getProducts().length > 0) {
            Set<Long> ids = Arrays.stream(c.getProductDetail().getProducts()).map(ProductDetail.MultiProduct::getProductId).collect(Collectors.toSet());
            return productServerApi.getMultiProducts(ids);
        } else {
            return new ArrayList<>();
        }
    }

    public Contract getDetailByProjectId(Long projectId) {
        return contractMapper.getDetailByProjectId(projectId);
    }

    public Long getProductSaleCount(Long productId) {
        List<ProductDetail> details = productDetailMapper.findByProductId(productId);
        return details.stream()
                .map(ProductDetail::getProducts)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .filter(multiProduct -> multiProduct.getProductId().equals(productId))
                .mapToLong(ProductDetail.MultiProduct::getAmount)
                .sum();
    }

    public boolean existsCustomer(Long customerId) {
        return contractMapper.existsCustomer(customerId);
    }

    public PageResultBo<Contract> findByCustomerId(ByCustomerIdQueryBo queryBo) {
        PageResultBo<Contract> bo = PageResultBo.of(queryBo, contractMapper::findByCustomerId);
        if (!CollectionUtils.isEmpty(bo.getRecords())) {
            Set<Long> ids = bo.getRecords().stream().map(Contract::getId).collect(Collectors.toSet());
            Map<Long, ProcessInstance> processInstanceMap = approvalServerApi.findByObjects(ProcessType.CONTRACT, ids)
                    .stream().collect(Collectors.toMap(ProcessInstance::getObject, Function.identity()));
            bo.getRecords().forEach(contract -> {
                contract.setProcessInstance(processInstanceMap.get(contract.getId()));
            });
        }
        return bo;
    }

    public List<Long> findSignedCustomerIdsInDays(int days) {
        return contractMapper.findSignedCustomerIdsInDays(days);
    }

    public long countByCustomerId(Long customerId) {
        return contractMapper.countByCustomerId(customerId);
    }

    public List<Long> findRepurchasedCustomerIds() {
        return contractMapper.findRepurchasedCustomerIds();
    }
}
