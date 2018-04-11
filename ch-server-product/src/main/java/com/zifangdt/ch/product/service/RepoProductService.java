package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.dto.product.RepoProductDetailDto1;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.base.dto.product.entity.RepoProduct;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.RepoQueryBo;
import com.zifangdt.ch.product.dto.RepoProductDetailDto;
import com.zifangdt.ch.product.mapper.RepoProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RepoProductService extends BaseService<RepoProduct, Long> {

    @Autowired
    RepoProductMapper repoProductMapper;

    @Autowired
    ProductService productService;

    @Autowired
    RepoService repoService;

    @Autowired
    StockChangeLogService stockChangeLogService;

    @Autowired
    OutStockService outStockService;

    public RepoProduct getOrCreate(Long productId, Long repoId) {
        RepoProduct repoProduct = repoProductMapper.findBy(productId, repoId);
        if (repoProduct == null) {
            repoProduct = new RepoProduct();
            repoProduct.setProductId(productId);
            repoProduct.setRepoId(repoId);
            repoProduct.setNumber(0);
            repoProduct.setPreOutNumber(0);
            repoProduct.setAvailableNumber(0);
            repoProduct.setContractNumber(0);
            save(repoProduct);
            return repoProduct;
        } else {
            return repoProduct;
        }
    }

    public void updateProductNumberInRepo(Long productId, Long repoId, Integer number) {
        RepoProduct repoProduct = getOrCreate(productId, repoId);
        repoProduct.setNumber(number);
        updateInternal(repoProduct);
    }

    public Page<RepoProductDetailDto1> findBy(RepoQueryBo bo) {
        Page<RepoProductDetailDto1> dtos =  PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> repoProductMapper.findListBy(bo));
        Map<Long, Long> signedNumber = outStockService.getPreOutProductNumber();
        for (RepoProductDetailDto1 dto: dtos) {
            dto.setContractNumber((int)(long)signedNumber.getOrDefault(dto.getProductId(), 0l));
        }
        return dtos;
    }

    /**
     * 产品入库
     *
     * @param stockChangeItem
     */
    public void inProduct(StockChangeItem stockChangeItem, Long stockChangeId) {
        // 修改产品库存
        Long productId = stockChangeItem.getProductId();
        Long repoId = stockChangeItem.getRepoId();
        Integer number = stockChangeItem.getRealCount();
        RepoProduct repoProduct = getOrCreate(productId, repoId);
        repoProduct.setNumber(repoProduct.getNumber() + number);
        updateInternal(repoProduct);

        // 生成库存流水
        stockChangeLogService.createInStockLog(stockChangeItem, stockChangeId, number, repoProduct.getNumber());
    }

    /**
     * 产品直接出库
     *
     * @param stockChangeItem
     */
    public void outProduct(StockChangeItem stockChangeItem, Long stockChangeId) {
        Long productId = stockChangeItem.getProductId();
        Long repoId = stockChangeItem.getRepoId();
        Integer number = stockChangeItem.getRealCount();
        RepoProduct repoProduct = getOrCreate(productId, repoId);
        Integer afterNumber = repoProduct.getNumber() - number;
        if (afterNumber < 0) {
            Product product = productService.get(productId);
            Repo repo = repoService.get(repoId);
            throw new DataInvalidException("产品: " + product.getName() + "在仓库: " + repo.getName() + "的库存不足");
        }
        repoProduct.setNumber(afterNumber);
        updateInternal(repoProduct);

        // 生成库存流水
        stockChangeLogService.createOutStockLog(stockChangeItem, stockChangeId,afterNumber + number, afterNumber);

    }

    /**
     * 产品预出库
     *
     * @param productId
     * @param repoId
     * @param number
     */
    public void preOutProduct(Long productId, Long repoId, Integer number) {
        RepoProduct repoProduct = getOrCreate(productId, repoId);
        Integer preOutNumber = repoProduct.getPreOutNumber() + number;
        repoProduct.setPreOutNumber(preOutNumber);
        updateInternal(repoProduct);
    }

    /**
     * 产品预出库确认出库
     *
     * @param stockChangeItem
     */
    public void preOutConfirm(StockChangeItem stockChangeItem, Long outStockChangeId) {
        Long productId = stockChangeItem.getProductId();
        Long repoId = stockChangeItem.getRepoId();
        Integer number = stockChangeItem.getRealCount();
        RepoProduct repoProduct = getOrCreate(productId, repoId);
        Integer preOutNumber = repoProduct.getPreOutNumber() - number;
        Integer totalNumber = repoProduct.getNumber() - number;
        if (preOutNumber < 0 || totalNumber < 0) {
            Product product = productService.get(productId);
            Repo repo = repoService.get(repoId);
            throw new DataInvalidException("产品: " + product.getName() + "在仓库: " + repo.getName() + "的库存不足");
        }
        repoProduct.setPreOutNumber(preOutNumber);
        repoProduct.setNumber(totalNumber);
        updateInternal(repoProduct);

        // 生成库存流水
        stockChangeLogService.createOutStockLog(stockChangeItem, outStockChangeId,totalNumber + number, totalNumber);
    }

    public List<RepoProductDetailDto> getListDetail(Long productId) {
        return repoProductMapper.findByProductId(productId);
    }

    @Override
    public void updateInternal(RepoProduct repoProduct){
        repoProduct.updateAvailableNumber();
        super.updateInternal(repoProduct);
    }
}
