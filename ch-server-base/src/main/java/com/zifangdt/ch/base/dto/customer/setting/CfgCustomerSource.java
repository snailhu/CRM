package com.zifangdt.ch.base.dto.customer.setting;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.setting.CustomerSourceTypeEnum;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "cfg_customer_source")
public class CfgCustomerSource extends AuditEntity<Long> {

    private Long dictId;

    private CustomerSourceTypeEnum relateType;

    private String name;

    @Transient
    private List<CfgCustomerSourceItem> items;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public CustomerSourceTypeEnum getRelateType() {
        return relateType;
    }

    public void setRelateType(CustomerSourceTypeEnum relateType) {
        this.relateType = relateType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<CfgCustomerSourceItem> getItems() {
        return items;
    }

    public void setItems(List<CfgCustomerSourceItem> items) {
        this.items = items;
    }

    public String getRelateTypeName(){
        return this.relateType.getName();
    }
}