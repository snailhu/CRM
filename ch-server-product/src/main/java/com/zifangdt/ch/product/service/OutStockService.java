package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.api.CustomerServerApi;
import com.zifangdt.ch.base.api.ManagerServerApi;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.product.StockChangeItemDetail;
import com.zifangdt.ch.base.dto.product.entity.OutStockChange;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import com.zifangdt.ch.base.dto.product.input.*;
import com.zifangdt.ch.base.dto.product.output.OutStockBrief;
import com.zifangdt.ch.base.dto.product.output.OutStockChangeBrief;
import com.zifangdt.ch.base.dto.product.output.OutStockChangeDetail;
import com.zifangdt.ch.base.dto.product.output.StockItemProductDto;
import com.zifangdt.ch.base.enums.product.OutStockStatus;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.OutStockQueryBo;
import com.zifangdt.ch.product.event.OutStockEvent;
import com.zifangdt.ch.product.event.PreOutStockEvent;
import com.zifangdt.ch.product.mapper.OutStockChangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutStockService extends BaseService<OutStockChange, Long> {

    @Autowired
    OutStockChangeMapper outStockChangeMapper;

    @Autowired
    ValidateService validateService;

    @Autowired
    StockChangeItemService stockChangeItemService;

    @Autowired
    RepoService repoService;

    @Autowired
    ProductService productService;

    @Autowired
    ContractServerApi contractServerApi;

    @Autowired
    CustomerServerApi customerServerApi;

    @Autowired
    ManagerServerApi managerServerApi;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private String generateNumber() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = LocalDate.now();
        Long count = outStockChangeMapper.countFor(date.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (count + 1);
    }

    /**
     * 自动项目材料出库
     *
     * @param dto
     * @return
     */
    public OutStockChange createProjectAutoOut(OutStockAutoProjectCreateDto dto) {
        OutStockChange change = dto.convertTo();
        List<OutStockChangeItemDto> products = dto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        List<Long> productIds = products.stream().map(OutStockChangeItemDto::getProductId).collect(Collectors.toList());

        validateService.validProductsExist(productIds);

        Map<Long, Integer> productOutCount = products
                .stream()
                .collect(Collectors.toMap(OutStockChangeItemDto::getProductId, OutStockChangeItemDto::getRealCount));

        Repo defaultRepo = repoService.getDefaultRepo();
//        validateService.validProductsInRepo(productOutCount, defaultRepo.getId());

        Contract contract = contractServerApi.detail(change.getContractId());
        Customer customer = customerServerApi.getOneCustomer(contract.getCustomerId());
        change.setCustomerName(customer.getName());
        change.setCustomerAddress(customer.getAddress());
        change.setCustomerContact(customer.getPhone());

        change.setStockNumber(generateNumber());
        change.setRelateName(contract.getPrintNumber());
        change.setRepoId(defaultRepo.getId());

        // 创建出库项
        List<StockChangeItem> items = new ArrayList<>();
        for (OutStockChangeItemDto itemOutDto : products) {
            StockChangeItem item = itemOutDto.convertTo();
            item.setCount(item.getRealCount());
            item.setStockChangeId(change.getId());
            item.setRepoId(change.getRepoId());
            items.add(item);
        }
        change.setItems(items.toArray(new StockChangeItem[0]));
        save(change);

        // 产品预出库
        eventPublisher.publishEvent(new PreOutStockEvent(this, change));
        return change;
    }

    /**
     * 创建项目材料出库，产品立即出库
     *
     * @param dto
     * @return
     */
    public Long createProjectOutStock(OutStockProjectManualDto dto) {

        OutStockChange outStockChange = dto.convertTo();

        List<OutStockChangeItemDto> products = dto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        // validate
        List<Long> productIds = products.stream().map(OutStockChangeItemDto::getProductId).collect(Collectors.toList());
        validateService.validProductsExist(productIds);

        validateService.validRepoExist(outStockChange.getRepoId());

        validateService.validContractExist(outStockChange.getContractId());

        validateService.validContractIsApproved(outStockChange.getContractId());

        Map<Long, Integer> productInCount = products
                .stream()
                .collect(Collectors.toMap(OutStockChangeItemDto::getProductId, OutStockChangeItemDto::getRealCount));

        validateService.validProductsInRepo(productInCount, outStockChange.getRepoId());

        Contract contract = contractServerApi.detail(outStockChange.getContractId());
        Customer customer = customerServerApi.getOneCustomer(contract.getCustomerId());
        outStockChange.setProjectId(contract.getProjectId());
        if (outStockChange.getCustomerName() == null) outStockChange.setCustomerName(customer.getName());
        if (outStockChange.getCustomerAddress() == null) outStockChange.setCustomerAddress(customer.getAddress());
        if (outStockChange.getCustomerContact() == null) outStockChange.setCustomerContact(customer.getPhone());
        outStockChange.setStockNumber(generateNumber());
        outStockChange.setRelateName(contract.getPrintNumber());

        List<StockChangeItem> items = new ArrayList<>();
        for (OutStockChangeItemDto itemOutDto : products) {
            StockChangeItem item = itemOutDto.convertTo();
            item.setCount(item.getRealCount());
            item.setStockChangeId(outStockChange.getId());
            item.setRepoId(outStockChange.getRepoId());
        }
        outStockChange.setItems(items.toArray(new StockChangeItem[0]));
        save(outStockChange);

        eventPublisher.publishEvent(new OutStockEvent(this, outStockChange, false));
        return outStockChange.getId();
    }

    public Long createOutStockRepoExchange(OutStockRepoExchangeDto dto) {
        OutStockChange outStockChange = dto.convertTo();

        List<OutStockChangeItemDto> products = dto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        // validate
        List<Long> productIds = products.stream().map(OutStockChangeItemDto::getProductId).collect(Collectors.toList());
        validateService.validProductsExist(productIds);

        validateService.validRepoExist(outStockChange.getRepoId());

        Map<Long, Integer> productInCount = products
                .stream()
                .collect(Collectors.toMap(OutStockChangeItemDto::getProductId, OutStockChangeItemDto::getRealCount));

        validateService.validProductsInRepo(productInCount, outStockChange.getRepoId());

        outStockChange.setStockNumber(generateNumber());
        List<StockChangeItem> items = new ArrayList<>();
        for (OutStockChangeItemDto itemOutDto : products) {
            StockChangeItem item = itemOutDto.convertTo();
            item.setCount(item.getRealCount());
            item.setStockChangeId(outStockChange.getId());
            item.setRepoId(outStockChange.getRepoId());
            items.add(item);
        }
        outStockChange.setItems(items.toArray(new StockChangeItem[0]));
        save(outStockChange);
        eventPublisher.publishEvent(new OutStockEvent(this, outStockChange, false));
        return outStockChange.getId();
    }

    public void stockOutConfirm(OutStockChange change, OutStockAutoProjectConfirmDto dto) {
        if (change.getStockStatus() == OutStockStatus.PreOutStock) {
            List<OutStockChangeItemDto> products = dto.getProducts();
            products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());
            // validate
            List<Long> productIds = products.stream().map(OutStockChangeItemDto::getProductId).collect(Collectors.toList());
            validateService.validProductsExist(productIds);

            validateService.validRepoExist(dto.getRepoId());

            Map<Long, Integer> productInCount = products
                    .stream()
                    .collect(Collectors.toMap(OutStockChangeItemDto::getProductId, OutStockChangeItemDto::getRealCount));

            validateService.validProductsInRepo(productInCount, dto.getRepoId());
            change.setStockStatus(OutStockStatus.OutStock);
            change.setStockTime(new Date());
            if (dto.getRepoId() != null) change.setRepoId(dto.getRepoId());
            if (dto.getDeliverTime() != null) change.setDeliverTime(dto.getDeliverTime());
            if (dto.getLogisticCompany() != null) change.setLogisticCompany(dto.getLogisticCompany());
            if (dto.getLogisticCost() != null) change.setLogisticCost(dto.getLogisticCost());
            if (dto.getLogisticNumber() != null) change.setLogisticNumber(dto.getLogisticNumber());
            List<StockChangeItem> items = new ArrayList<>();
            for (OutStockChangeItemDto itemOutDto : products) {
                StockChangeItem item = itemOutDto.convertTo();
                item.setCount(item.getRealCount());
                item.setStockChangeId(change.getId());
                item.setRepoId(change.getRepoId());
                items.add(item);
            }
            change.setItems(items.toArray(new StockChangeItem[0]));
            updateInternal(change);
            eventPublisher.publishEvent(new OutStockEvent(this, change, true));
            managerServerApi.changeStoreStatus(change.getProcedureId(), OutStockStatus.OutStock);
        } else {
            throw new DataInvalidException("出库单不是未出库状态");
        }
    }

    private void validProjectExist(Long projectId) {
        // TODO: 调用project service
    }

    private String getProjectName(Long projectId) {
        // TODO: 调用project service
        return "项目：" + projectId;
    }

    public OutStockChangeDetail getOne(Long id) {
        OutStockChange outStockChange = get(id);
        if (outStockChange == null) throw new DataInvalidException("入库单id:" + id + "不存在");
        List<StockChangeItem> items = stockChangeItemService.getOutStockItem(id);

        OutStockChangeDetail inStockChangeDetail = new OutStockChangeDetail();
        inStockChangeDetail.convertFrom(outStockChange);
        inStockChangeDetail.setRepoName(repoService.get(outStockChange.getRepoId()).getName());

        List<StockChangeItemDetail> products = new ArrayList<>();
        for (StockChangeItem item : items) {
            StockChangeItemDetail detail = new StockChangeItemDetail();
            detail.convertFrom(item);
            Product product = productService.get(item.getProductId());
            detail.setProductName(product.getName());
            detail.setProductNumber(product.getNumber());
            products.add(detail);
        }
        inStockChangeDetail.setProducts(products);
        return inStockChangeDetail;
    }

    public Page<OutStockChangeBrief> queryBo(OutStockQueryBo bo) {

        Page<OutStockChange> page = PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> outStockChangeMapper.findBy(bo));
        Page<OutStockChangeBrief> page1 = new Page<>(page.getPageNum(), page.getPageSize());
        page1.setTotal(page.getTotal());
        for (OutStockChange outStockChange : page) {
            OutStockChangeBrief brief = new OutStockChangeBrief().convertFrom(outStockChange);
            Repo repo = repoService.get(outStockChange.getRepoId());
            brief.setRepoName(repo.getName());
            brief.setRepoType(repo.getRepoType());
            page1.add(brief);
        }
        return page1;
    }

    public List<OutStockBrief> getOutStockForProject(Long projectId) {
        List<OutStockChange> changes = outStockChangeMapper.findForProject(projectId);
        List<OutStockBrief> result = changes.stream().map(OutStockBrief::new).collect(Collectors.toList());
        for (OutStockBrief outStockBrief : result) {
            List<StockChangeItem> items = stockChangeItemService.getOutStockItem(outStockBrief.getId());
            List<StockItemProductDto> products = items.stream().map(item -> {
                StockItemProductDto dto = new StockItemProductDto(item);
                Product product = productService.get(dto.getProductId());
                dto.setCost(product.getCost());
                dto.setName(product.getName());
                dto.setNumber(product.getNumber());
                dto.setProductType(product.getType());
                dto.setProductUnitName(product.getUnitType().getName());
                return dto;
            }).collect(Collectors.toList());
            outStockBrief.setProducts(products);
        }
        return result;
    }

    public Map<Long, Long> getPreOutProductNumber() {
        List<OutStockChange> changes = outStockChangeMapper.findPreOut();
        return changes.stream()
                .map(OutStockChange::getItems)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(StockChangeItem::getProductId, Collectors.summingLong(StockChangeItem::getRealCount)));
    }
}
