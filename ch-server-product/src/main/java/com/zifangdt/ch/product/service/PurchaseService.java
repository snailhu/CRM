package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.ApprovalServerApi;
import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.dto.FromEntity;
import com.zifangdt.ch.base.dto.approval.ApprovalOperateBo;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.approval.RunningTrack;
import com.zifangdt.ch.base.dto.product.PurchaseDetailDto;
import com.zifangdt.ch.base.dto.product.PurchaseDto;
import com.zifangdt.ch.base.dto.product.PurchaseItemDto;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.Purchase;
import com.zifangdt.ch.base.dto.product.entity.PurchaseItem;
import com.zifangdt.ch.base.dto.product.output.PurchaseInStockDto;
import com.zifangdt.ch.base.dto.ticket.output.PurchaseItemDetailDto;
import com.zifangdt.ch.base.enums.product.PurchaseStatus;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.PurchaseQueryBo;
import com.zifangdt.ch.product.event.PurchaseCreateEvent;
import com.zifangdt.ch.product.mapper.PurchaseMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseService extends BaseService<Purchase, Long> implements FromEntity<Purchase, PurchaseDetailDto> {
    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    ProviderService providerService;

    @Autowired
    ProductService productService;

    @Autowired
    ApprovalServerApi approvalServerApi;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    InStockService inStockService;

    @Autowired
    CommonServerApi commonServerApi;

    private String generateNumber() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = LocalDate.now();
        Long count = purchaseMapper.countFor(date.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (count + 1);
    }

    /**
     * 获取采购列表
     *
     * @param bo
     * @return
     */
    public Page<PurchaseDetailDto> getPurchaseList(PurchaseQueryBo bo) {
        Page<Purchase> page = PageHelper.startPage(bo.getPage(), bo.getSize()).doSelectPage(
                () -> purchaseMapper.findBy(bo)
        );
        Page<PurchaseDetailDto> page1 = new Page<>(page.getPageNum(), page.getPageSize());
        page1.setTotal(page.getTotal());
        for (Purchase purchase : page) {
            PurchaseDetailDto dto = convertFrom(purchase);
            dto.setProviderName(providerService.get(purchase.getProviderId()).getName());
            page1.add(dto);
        }
        return page1;
    }

    public PurchaseDetailDto getOne(Long id) {
        return getOne(id, null);
    }

    /**
     * 获取采购详情
     *
     * @param id
     * @return
     */
    public PurchaseDetailDto getOne(Long id, ProcessOverview processOverview) {
        Purchase purchase = get(id);
        if (purchase == null) throw new DataNotFoundException("找不到采购单");
        List<PurchaseItem> purchaseItems = Arrays.asList(purchase.getPurchaseItems());
        List<PurchaseItemDetailDto> dtos = purchaseItems
                .stream()
                .map(PurchaseItemDetailDto::new)
                .collect(Collectors.toList());


        PurchaseDetailDto dto = convertFrom(purchase);
        if (StringUtils.isNotEmpty(purchase.getAppendUrl())) {
            dto.setAppendUrlDetail(commonServerApi.fileInfo(Arrays.asList(purchase.getAppendUrl().split(","))));
        }
        dto.setProviderName(providerService.get(purchase.getProviderId()).getName());
        dto.setPurchaseItemDtos(dtos);
        if (processOverview == null) {
            processOverview = approvalServerApi.overview(purchase.getProcessInstanceId());
        }
        dto.setProcessStatus(processOverview.getProcess().getStatus());
        dto.setProcessOverview(processOverview);

        List<RunningTrack> tracks = approvalServerApi.tracks(purchase.getProcessInstanceId());
        dto.setTracks(tracks);
        dto.setInStocks(inStockService.getChangeForPurchase(id).stream().map(PurchaseInStockDto::new).collect(Collectors.toList()));
        if (dto.getPurchaseStatus() == PurchaseStatus.Cancel) {
            dto.setCancelNum(dto.getTotalNum() - dto.getInNum());
        }
        return dto;
    }

    /**
     * 新建采购单
     *
     * @param purchaseDto
     */
    public Long create(PurchaseDto purchaseDto) {
        if (!providerService.exists(purchaseDto.getProviderId())) {
            throw new DataInvalidException("供应商不存在");
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PurchaseItem> items = new ArrayList<>();
        for (PurchaseItemDto purchaseItemDto : purchaseDto.getPurchaseItemDtos()) {
            PurchaseItem purchaseItem = purchaseItemDto.convertTo();
            Product product = productService.get(purchaseItem.getProductId());
            purchaseItem.setPrice(product.getCost());
            purchaseItem.setProduct(product);
            items.add(purchaseItem);
            totalAmount = totalAmount.add(purchaseItem.getPrice().multiply(BigDecimal.valueOf(purchaseItem.getCount())));
        }

        // 保存采购单
        Purchase purchase = purchaseDto.convertTo();
        purchase.setPurchaseItems(items.toArray(new PurchaseItem[0]));
        purchase.setNum(generateNumber());
        purchase.setTotalAmount(totalAmount);
        purchase.setPurchaseStatus(PurchaseStatus.NotStart);
        purchase.setTotalNum(purchaseDto.getTotalProductNum());
        save(purchase);

        eventPublisher.publishEvent(new PurchaseCreateEvent(this, purchase));
        return purchase.getId();
    }

    /**
     * 取消采购单
     *
     * @param purchase
     */
    public void cancelPurchase(Purchase purchase, String comment) {
        PurchaseStatus purchaseStatus = purchase.getPurchaseStatus();
        if (purchaseStatus == PurchaseStatus.Cancel || purchaseStatus == PurchaseStatus.Finish) {
            throw new DataInvalidException("当前状态不能取消");
        }
        if (purchase.getPurchaseStatus() == PurchaseStatus.NotStart) {
            approvalServerApi.setStatusToCanceled(purchase.getProcessInstanceId());
        }
        purchase.setPurchaseStatus(PurchaseStatus.Cancel);
        purchase.setComment(comment);
        updateInternal(purchase);
    }

    @Override
    public PurchaseDetailDto convertFrom(Purchase entity) {
        PurchaseDetailDto dto = new PurchaseDetailDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public void handleApproved(ProcessInstance processInstance) {
        Purchase purchase = get(processInstance.getObject());
        purchase.setPurchaseStatus(PurchaseStatus.OnGoing);
        updateInternal(purchase);
    }

    public Long updatePurchase(Long id, PurchaseDto purchaseDto) {
        if (!providerService.exists(purchaseDto.getProviderId())) {
            throw new DataInvalidException("供应商不存在");
        }
        Purchase purchase = get(id);
        if (purchase == null) throw new DataNotFoundException("找不到对应的采购单");

//        List<PurchaseItem> oldItems = purchaseItemService.findByPurchase(id);
//        purchaseItemService.deleteMulti(oldItems.stream().map(PurchaseItem::getId).collect(Collectors.toSet()));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PurchaseItem> items = new ArrayList<>();
        for (PurchaseItemDto purchaseItemDto : purchaseDto.getPurchaseItemDtos()) {
            PurchaseItem purchaseItem = purchaseItemDto.convertTo();
            Product product = productService.get(purchaseItem.getProductId());
            purchaseItem.setPrice(product.getPrice());
            purchaseItem.setProduct(product);
            items.add(purchaseItem);
            totalAmount = totalAmount.add(purchaseItem.getPrice().multiply(BigDecimal.valueOf(purchaseItem.getCount())));
        }

        // 保存采购单
        purchase.setPurchaseItems(items.toArray(new PurchaseItem[0]));
        purchase.setProviderId(purchaseDto.getProviderId());
        purchase.setName(purchaseDto.getName());
        purchase.setTotalAmount(totalAmount);
        purchase.setTotalNum(purchaseDto.getTotalProductNum());
        updateInternal(purchase);

        // 更新审批状态，进入下一步
        ProcessOverview processOverview = approvalServerApi.overview(purchase.getProcessInstanceId());
        ApprovalOperateBo bo = new ApprovalOperateBo();
        bo.setProcessId(processOverview.getProcess().getId());
        bo.setRemark("修改采购单");
        approvalServerApi.resolve(bo);
        return id;
    }
}
