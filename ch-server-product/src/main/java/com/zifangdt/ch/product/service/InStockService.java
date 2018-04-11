package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.dto.product.*;
import com.zifangdt.ch.base.dto.product.entity.*;
import com.zifangdt.ch.base.dto.product.output.InStockChangeBrief;
import com.zifangdt.ch.base.dto.product.output.InStockChangeDetail;
import com.zifangdt.ch.base.enums.StockChangeType;
import com.zifangdt.ch.base.enums.product.InStockType;
import com.zifangdt.ch.base.enums.product.PurchaseStatus;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.InStockQueryBo;
import com.zifangdt.ch.product.event.InStockEvent;
import com.zifangdt.ch.product.mapper.InStockChangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InStockService extends BaseService<InStockChange, Long> {

    @Autowired
    InStockChangeMapper inStockChangeMapper;

    @Autowired
    ProductService productService;

    @Autowired
    StockChangeItemService stockChangeItemService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    RepoService repoService;

    @Autowired
    ValidateService validateService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private String generateNumber() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = LocalDate.now();
        Long count = inStockChangeMapper.countFor(date.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (count + 1);
    }

    /**
     * 创建采购入库
     *
     * @return
     */
    public Long createPurchaseInStock(InStockPurchaseDto inStockPurchaseDto) {

        Purchase purchase = purchaseService.get(inStockPurchaseDto.getPurchaseId());
        if (purchase == null) throw new DataInvalidException("采购单不存在");
        if (purchase.getPurchaseStatus() != PurchaseStatus.OnGoing) throw new DataInvalidException("采购单不处在采购状态，不允许入库");
        InStockChange inStockChange = inStockPurchaseDto.convertTo();

        List<StockChangeItemDto> products = inStockPurchaseDto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        // validate
        List<Long> productIds = products.stream().map(StockChangeItemDto::getProductId).collect(Collectors.toList());

        validateService.validProductsExist(productIds);

        validPurchaseExist(inStockChange.getPurchaseId());

        validateService.validRepoExist(inStockChange.getRepoId());

        validProductsInPurchase(productIds, inStockPurchaseDto.getPurchaseId());

        Map<Long, Integer> productInCount = products
                .stream()
                .collect(Collectors.toMap(StockChangeItemDto::getProductId, StockChangeItemDto::getRealCount));
        validInProductsCount(productInCount, inStockChange.getPurchaseId());

        // save
        inStockChange.setStockNumber(generateNumber());
        inStockChange.setRelateName(purchaseService.get(inStockChange.getPurchaseId()).getName());
        Map<Long, Integer> purchaseRemainCount = getPurchaseRemainCount(inStockPurchaseDto.getPurchaseId());
        List<StockChangeItem> items = new ArrayList<>();
        for (StockChangeItemDto stockChangeItemDto : products) {
            StockChangeItem stockChangeItem = stockChangeItemDto.convertTo();
            stockChangeItem.setRepoId(inStockChange.getRepoId());
            stockChangeItem.setStockChangeId(inStockChange.getId());
            stockChangeItem.setStockChangeType(StockChangeType.In);
            stockChangeItem.setCount(purchaseRemainCount.get(stockChangeItem.getProductId()));
            items.add(stockChangeItem);
        }
        inStockChange.setItems(items.toArray(new StockChangeItem[0]));
        save(inStockChange);

        eventPublisher.publishEvent(new InStockEvent(this, inStockChange));
        return inStockChange.getId();
    }

    /**
     * 创建材料回收入库
     *
     * @return
     */
    public Long createMaterialReturn(InStockProjectDto dto) {
        InStockChange inStockChange = dto.convertTo();

        List<StockChangeItemDto> products = dto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        // validate
        List<Long> productIds = products.stream().map(StockChangeItemDto::getProductId).collect(Collectors.toList());

        validateService.validProductsExist(productIds);

        validProjectExist(inStockChange.getProjectId());

        validateService.validRepoExist(inStockChange.getRepoId());

        // save
        inStockChange.setStockNumber(generateNumber());
        inStockChange.setRelateName(getProjectName(inStockChange.getProjectId()));

        List<StockChangeItem> items = new ArrayList<>();
        for (StockChangeItemDto stockChangeItemDto : products) {
            StockChangeItem stockChangeItem = stockChangeItemDto.convertTo();
            stockChangeItem.setCount(stockChangeItem.getRealCount());
            stockChangeItem.setRepoId(inStockChange.getRepoId());
            stockChangeItem.setStockChangeId(inStockChange.getId());
            stockChangeItem.setStockChangeType(StockChangeType.In);
            items.add(stockChangeItem);
        }
        inStockChange.setItems(items.toArray(new StockChangeItem[0]));
        save(inStockChange);
        eventPublisher.publishEvent(new InStockEvent(this, inStockChange));
        return inStockChange.getId();
    }

    /**
     * 创建仓库调拨入库
     *
     * @return
     */
    public Long createStockExchange(InStockChangeDto inStockChangeDto) {
        InStockChange inStockChange = inStockChangeDto.convertTo();

        List<StockChangeItemDto> products = inStockChangeDto.getProducts();
        products = products.stream().filter(item -> item.getRealCount() > 0).collect(Collectors.toList());

        // validate
        List<Long> productIds = products.stream().map(StockChangeItemDto::getProductId).collect(Collectors.toList());

        validateService.validProductsExist(productIds);

        validateService.validRepoExist(inStockChange.getRepoId());

        // save
        inStockChange.setStockNumber(generateNumber());
        inStockChange.setInStockType(InStockType.RepoExchange);

        List<StockChangeItem> items = new ArrayList<>();
        for (StockChangeItemDto stockChangeItemDto : products) {
            StockChangeItem stockChangeItem = stockChangeItemDto.convertTo();
            stockChangeItem.setCount(stockChangeItem.getRealCount());
            stockChangeItem.setRepoId(inStockChange.getRepoId());
            stockChangeItem.setStockChangeId(inStockChange.getId());
            stockChangeItem.setStockChangeType(StockChangeType.In);
            items.add(stockChangeItem);
        }
        inStockChange.setItems(items.toArray(new StockChangeItem[0]));
        save(inStockChange);

        eventPublisher.publishEvent(new InStockEvent(this, inStockChange));
        return inStockChange.getId();
    }

    private void validProjectExist(Long projectId) {
        // TODO: 调用project service
    }

    private String getProjectName(Long projectId) {
        // TODO: 调用project service
        return "项目：" + projectId;
    }

    public InStockChangeDetail getOne(Long id) {
        InStockChange inStockChange = get(id);
        if (inStockChange == null) throw new DataInvalidException("入库单id:" + id + "不存在");
        List<StockChangeItem> items = stockChangeItemService.getInStockItem(id);

        InStockChangeDetail inStockChangeDetail = new InStockChangeDetail();
        inStockChangeDetail.convertFrom(inStockChange);
        inStockChangeDetail.setRepoName(repoService.get(inStockChange.getRepoId()).getName());

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

    public Page<InStockChangeBrief> queryBo(InStockQueryBo bo) {
        Page<InStockChange> page = PageHelper.startPage(bo.getPage(), bo.getSize()).doSelectPage(() -> inStockChangeMapper.findBy(bo));
        Page<InStockChangeBrief> page1 = new Page<>(page.getPageNum(), page.getPageSize());
        page1.setTotal(page.getTotal());
        for (InStockChange inStockChange : page) {
            InStockChangeBrief brief = new InStockChangeBrief().convertFrom(inStockChange);
            Repo repo = repoService.get(inStockChange.getRepoId());
            brief.setRepoName(repo.getName());
            brief.setRepoType(repo.getRepoType());
            page1.add(brief);
        }
        return page1;
    }

    /**
     * 采购单存在采购单中
     *
     * @param purchaseId
     */
    private void validPurchaseExist(Long purchaseId) {
        if (!purchaseService.exists(purchaseId)) throw new DataInvalidException("采购单id:" + purchaseId + "不存在");
    }

    /**
     * 产品在采购单中
     *
     * @param productId
     * @param purchaseId
     */
    private void validProductInPurchase(Long productId, Long purchaseId) {
        List<PurchaseItem> result = Arrays.asList(purchaseService.get(purchaseId).getPurchaseItems());
        Map<Long, PurchaseItem> map = result.stream().collect(Collectors.toMap(PurchaseItem::getProductId, Function.identity()));
        Product product = productService.get(productId);
        if (!map.containsKey(productId)) throw new DataInvalidException("产品" + product.getName() + "不在该采购单");
    }

    private void validProductsInPurchase(List<Long> productIds, Long purchaseId) {
        List<PurchaseItem> result = Arrays.asList(purchaseService.get(purchaseId).getPurchaseItems());
        Map<Long, PurchaseItem> map = result.stream().collect(Collectors.toMap(PurchaseItem::getProductId, Function.identity()));
        for (Long productId : productIds) {
            Product product = productService.get(productId);
            if (!map.containsKey(productId)) throw new DataInvalidException("产品" + product.getName() + "不在该采购单");
        }
    }

    /**
     * 采购单剩余数量大于入库数量
     *
     * @param productId
     * @param purchaseId
     * @param count
     */
    private void validInProductCount(Long productId, Long purchaseId, Integer count) {
        List<PurchaseItem> purchaseItems = Arrays.asList(purchaseService.get(purchaseId).getPurchaseItems());
        Map<Long, Integer> productCount = purchaseItems.stream().collect(Collectors.toMap(PurchaseItem::getProductId, PurchaseItem::getCount));
        List<InStockChange> changes = inStockChangeMapper.getByPurchase(purchaseId);
        for (InStockChange inStockChange : changes) {

        }
        if (count > productCount.get(productId)) throw new DataInvalidException("产品" + productId + "入库数量不能多余剩余待入库数量");
    }

    private void validInProductsCount(Map<Long, Integer> productInCount, Long purchaseId) {
        Map<Long, Integer> purchaseRemainCount = getPurchaseRemainCount(purchaseId);
        for (Long productId : productInCount.keySet()) {
            if (productInCount.get(productId) > purchaseRemainCount.get(productId)) {
                Product product = productService.get(productId);
                throw new DataInvalidException("产品:" + product.getName() + ",入库数量超过采购待入库数量");
            }
        }
    }

    /**
     * 获取采购单剩余待入库产品数量
     *
     * @param purchaseId
     */
    public Map<Long, Integer> getPurchaseRemainCount(Long purchaseId) {
        List<PurchaseItem> purchaseItems = Arrays.asList(purchaseService.get(purchaseId).getPurchaseItems());
        Map<Long, Integer> productCount = purchaseItems.stream().collect(Collectors.toMap(PurchaseItem::getProductId, PurchaseItem::getCount));

        List<StockChangeItem> stockChangeItems = stockChangeItemService.getPurchaseStockItem(purchaseId);
        for (StockChangeItem stockChangeItem : stockChangeItems) {
            Integer remain = productCount.get(stockChangeItem.getProductId());
            remain -= stockChangeItem.getRealCount();
            productCount.put(stockChangeItem.getProductId(), remain);
        }
        return productCount;
    }

    public List<InStockChange> getChangeForPurchase(long purchaseId){
        List<InStockChange> changes = inStockChangeMapper.getByPurchase(purchaseId);
        return changes;
    }
}
