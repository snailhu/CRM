package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "repo_product")
public class RepoProduct extends AuditEntity<Long> implements Serializable{

    private Long productId;

    // 总库存 number = availableNumber + preOutNumber
    private Integer number;

    // 可用库存
    private Integer availableNumber;

    // 待发库存: 待出库数量，当待出库变成已出库，减去已出库数量
    private Integer preOutNumber;

    // 签约库存：合同签订时增加，合同项目出库时减少，最小为0
    private Integer contractNumber;

    private Long repoId;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    public Integer getAvailableNumber() {
        return availableNumber;
    }

    public RepoProduct setAvailableNumber(Integer availableNumber) {
        this.availableNumber = availableNumber;
        return this;
    }

    public Integer getPreOutNumber() {
        return preOutNumber;
    }

    public RepoProduct setPreOutNumber(Integer preOutNumber) {
        this.preOutNumber = preOutNumber;
        return this;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public RepoProduct setContractNumber(Integer contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public void updateAvailableNumber() {
        Integer afterAvailableNumber = getNumber() - getPreOutNumber();
        if (afterAvailableNumber < 0 ) afterAvailableNumber = 0;
        setAvailableNumber(afterAvailableNumber);
    }
}