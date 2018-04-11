package com.zifangdt.ch.base.dto.syssetting;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.PriceSheetTypeEnum;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import java.io.Serializable;

@Table(name = "price_sheet_step")
public class PriceSheetStep extends AuditEntity<Long> implements Serializable{
    private Long userId;

    private Long organizationId;

    private Boolean isOrganizationCharger;

    private Boolean isUser;

    private PriceSheetTypeEnum type;

    private Integer sort;

    @NotEmpty
    @Length(max = 32)
    private String name;

    @Transient
    private String username;

    @Transient
    private String organizationName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getIsOrganizationCharger() {
        return isOrganizationCharger;
    }

    public void setIsOrganizationCharger(Boolean isOrganizationCharger) {
        this.isOrganizationCharger = isOrganizationCharger;
    }

    public Boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(Boolean isUser) {
        this.isUser = isUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceSheetTypeEnum getType() {
        return type;
    }

    public PriceSheetStep setType(PriceSheetTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public PriceSheetStep setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}