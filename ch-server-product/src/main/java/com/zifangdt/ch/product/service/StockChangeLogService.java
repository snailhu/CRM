package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.constant.NoticeTags;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import com.zifangdt.ch.base.dto.product.entity.StockChangeLog;
import com.zifangdt.ch.base.dto.product.output.ProjectOutStockProduct;
import com.zifangdt.ch.base.dto.product.output.StockChangeLogBrief;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.StockChangeLogQueryBo;
import com.zifangdt.ch.product.mapper.StockChangeLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StockChangeLogService extends BaseService<StockChangeLog, Long> {

    @Autowired
    StockChangeLogMapper stockChangeLogMapper;

    @Autowired
    ProductService productService;

    @Autowired
    CommonServerApi commonServerApi;

    @Autowired
    ConfigItemService configItemService;

    public Page<StockChangeLogBrief> getList(StockChangeLogQueryBo bo) {
        return PageHelper
                .startPage(bo.getPage(), bo.getSize()).doSelectPage(
                        () -> stockChangeLogMapper.getList(bo));
    }

    public Page<StockChangeLogBrief> getListForProduct(Long productId, Integer page, Integer size) {
        return PageHelper
                .startPage(page, size).doSelectPage(
                        () -> stockChangeLogMapper.getListForProduct(productId));
    }

    /**
     * 创建一条入库库存流水
     *
     * @param stockChangeItem
     */
    public void createInStockLog(StockChangeItem stockChangeItem, Long stockChangeId, Integer before, Integer after) {
        StockChangeLog log = stockChangeItem.makeLog();
        log.setBeforeNumber(before);
        log.setAfterNumber(after);
        log.setStockChangeId(stockChangeId);
        save(log);
    }

    /**
     * 创建一条出库库存流水
     *
     * @param stockChangeItem
     */
    public void createOutStockLog(StockChangeItem stockChangeItem, Long stockChangeId, Integer before, Integer after) {
        StockChangeLog log = stockChangeItem.makeLog();
        log.setBeforeNumber(before);
        log.setAfterNumber(after);
        log.setStockChangeId(stockChangeId);
        save(log);
        Product product = productService.get(log.getProductId());
        Integer normalStockNumber = productService.getProductNormalStockNumber(log.getProductId());
        if (normalStockNumber < product.getAlertNumber()) {
            Set<Long> userIds = configItemService.permittedUsers(ConfigType.STOCK_ALERT_STAFF);
            commonServerApi.saveNotice(Notice.newBuilder()
                    .type(NoticeType.STOCK_LOW_ALERT, product.getName())
                    .name("库存预警")
                    .tag(NoticeTags.PRODUCT)
                    .redirect(RedirectType.Product, product.getId())
                    .receivers(userIds)
                    .build());
        }
    }

    public List<ProjectOutStockProduct> getForProject(Long projectId) {
        return stockChangeLogMapper.getForProject(projectId);
    }

    public Long getOutNumberForProduct(Long productId) {
        return stockChangeLogMapper.getOutNumberForProduct(productId);
    }
}
