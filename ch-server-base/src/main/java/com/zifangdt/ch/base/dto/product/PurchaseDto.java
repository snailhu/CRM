package com.zifangdt.ch.base.dto.product;

import com.zifangdt.ch.base.dto.ToEntity;
import com.zifangdt.ch.base.dto.product.entity.Purchase;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import java.util.List;

import javax.validation.constraints.NotNull;

public class PurchaseDto implements ToEntity<Purchase> {
    @NotEmpty
    private String name;
    @NotNull
    private Long providerId;
    @NotEmpty
    private List<PurchaseItemDto> purchaseItemDtos;

    private  String remark;

    private  String appendUrl[];


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getAppendUrl() {
        return appendUrl;
    }

    public void setAppendUrl(String[] appendUrl) {
        this.appendUrl = appendUrl;
    }

    public String getName() {
        return name;
    }

    public PurchaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<PurchaseItemDto> getPurchaseItemDtos() {
        return purchaseItemDtos;
    }

    public PurchaseDto setPurchaseItemDtos(List<PurchaseItemDto> purchaseItemDtos) {
        this.purchaseItemDtos = purchaseItemDtos;
        return this;
    }

    @Override
    public Purchase convertTo() {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(this, purchase);
        return purchase;
    }

    public Long getProviderId() {
        return providerId;
    }

    public PurchaseDto setProviderId(Long providerId) {
        this.providerId = providerId;
        return this;
    }

    public Integer getTotalProductNum() {
        return getPurchaseItemDtos().stream().map(PurchaseItemDto::getCount).reduce(Integer::sum).get();
    }

}
