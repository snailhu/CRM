package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.dto.product.entity.InStockChange;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StockChangeItemService {

    @Autowired
    InStockService inStockService;

    @Autowired
    OutStockService outStockService;

    /**
     * 获取采购单所有入库单详情
     *
     * @param purchaseId
     * @return
     */
    List<StockChangeItem> getPurchaseStockItem(Long purchaseId) {
        List<InStockChange> changes = inStockService.getChangeForPurchase(purchaseId);
        return changes.stream()
                .map(InStockChange::getItems)
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    /**
     * 获取一次入库的所有入库产品
     *
     * @param stockChangeId
     * @return
     */
    public List<StockChangeItem> getInStockItem(Long stockChangeId) {
        return Arrays.asList(inStockService.get(stockChangeId).getItems());
    }

    /**
     * 获取一次出库的所有出库产品
     *
     * @param stockChangeId
     * @return
     */
    public List<StockChangeItem> getOutStockItem(Long stockChangeId) {
        return Arrays.asList(outStockService.get(stockChangeId).getItems());
    }
}
