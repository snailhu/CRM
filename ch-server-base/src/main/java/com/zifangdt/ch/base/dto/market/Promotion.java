package com.zifangdt.ch.base.dto.market;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.PromotionStatusEnum;
import com.zifangdt.ch.base.enums.PromotionTypeEnum;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "promotion")
public class Promotion extends AuditEntity<Long> {
    private String name;

    private PromotionStatusEnum status;

    private String url;

    private PromotionTypeEnum type;

    private String components;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public PromotionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PromotionStatusEnum status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public PromotionTypeEnum getType() {
        return type;
    }

    public void setType(PromotionTypeEnum type) {
        this.type = type;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }
}