package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.base.dto.product.entity.RepoProduct;
import com.zifangdt.ch.base.exception.DataInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ValidateService {

    @Autowired
    ProductService productService;

    @Autowired
    RepoService repoService;

    @Autowired
    RepoProductService repoProductService;

    @Autowired
    ContractServerApi contractServerApi;

    /**
     * 多个产品在系统中存在
     *
     * @param products
     */
    public void validProductsExist(List<Long> products) {
        products.forEach(productId -> validProductExist(productId));
    }

    /**
     * 产品在系统中存在
     *
     * @param productId
     */
    private void validProductExist(Long productId) {
        if (!productService.exists(productId)) {
            String msg = String.format("[产品id:%d]不存在", productId);
            throw new DataInvalidException(msg);
        }
    }

    public void validRepoExist(Long repoId) {
        Repo repo = repoService.get(repoId);
        if (repo == null) {
            String msg = String.format("[仓库id:%d]不存在", repoId);
            throw new DataInvalidException(msg);
        }
        if (!repo.getIsValid()) {
            String msg = String.format("[仓库:%s]未启用", repo.getName());
            throw new DataInvalidException(msg);
        }
    }

    /**
     * 产品有足够的库存
     * @param productCount
     * @param repoId
     */
    public void validProductsInRepo(Map<Long, Integer> productCount, Long repoId) {
        for(Long productId: productCount.keySet()) {
            RepoProduct repoProduct = repoProductService.getOrCreate(productId, repoId);
            if (repoProduct.getAvailableNumber() < productCount.get(productId)) {
                Product product = productService.get(productId);
                Repo repo = repoService.get(repoId);
                String msg = String.format("[产品:%s]在[仓库:%s]的库存不足",product.getName(), repo.getName());
                throw new DataInvalidException(msg);
            }
        }
    }

    public void validContractExist(Long contractId) {
        Contract contract = contractServerApi.detail(contractId);
        if (contract == null) throw new DataInvalidException("合同id=" + contractId + "不存在");
    }

    public void validContractIsApproved(Long contractId) {
        Contract contract = contractServerApi.detail(contractId);
        if (contract.getProcessId() == null) throw new DataInvalidException("合同id=" + contractId + "没有审批通过");
    }
}
