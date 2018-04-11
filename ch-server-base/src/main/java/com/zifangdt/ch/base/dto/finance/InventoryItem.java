package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.validation.ConfiguredOption;

import java.math.BigDecimal;

/**
 * Created by 袁兵 on 2018/2/7.
 */
public class InventoryItem {
    @NamedProperty
    @ConfiguredOption(ConfigType.journalTypes)
    private Long type;
    private BigDecimal money;

    public InventoryItem() {
    }

    public InventoryItem(Long type, BigDecimal money) {
        this.type = type;
        this.money = money;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
