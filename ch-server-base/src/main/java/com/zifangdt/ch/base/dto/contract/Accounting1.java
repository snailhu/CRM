package com.zifangdt.ch.base.dto.contract;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.pair.AccountingType;
import com.zifangdt.ch.base.validation.PositiveNumber;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_contract_accounting1")
public class Accounting1 implements Serializable {
    private static final long serialVersionUID = 5767354319676008533L;
    @Id
    private Long contractId;
    @NotNull
    @PositiveNumber
    private BigDecimal retailPrice;
    @NotNull
    @NamedProperty
    private AccountingType type;
    @NotNull
    @PositiveNumber
    private BigDecimal floorPrice;
    @NotNull
    private Boolean hasGift;
    @PositiveNumber
    private BigDecimal giftCost;
    @NotNull
    @PositiveNumber
    private BigDecimal designCost;
    @NotNull
    @PositiveNumber
    private BigDecimal extendServiceCost;
    @NotNull
    @PositiveNumber
    private BigDecimal totalCost;
    private String remark;

    public Boolean getHasGift() {
        return hasGift;
    }

    public void setHasGift(Boolean hasGift) {
        this.hasGift = hasGift;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public AccountingType getType() {
        return type;
    }

    public void setType(AccountingType type) {
        this.type = type;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
    }

    public BigDecimal getGiftCost() {
        return giftCost;
    }

    public void setGiftCost(BigDecimal giftCost) {
        this.giftCost = giftCost;
    }

    public BigDecimal getDesignCost() {
        return designCost;
    }

    public void setDesignCost(BigDecimal designCost) {
        this.designCost = designCost;
    }

    public BigDecimal getExtendServiceCost() {
        return extendServiceCost;
    }

    public void setExtendServiceCost(BigDecimal extendServiceCost) {
        this.extendServiceCost = extendServiceCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
