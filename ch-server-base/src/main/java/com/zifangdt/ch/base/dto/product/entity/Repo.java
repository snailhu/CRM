package com.zifangdt.ch.base.dto.product.entity;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.product.RepoType;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 仓库
 */
@Table(name = "repo")
public class Repo extends AuditEntity<Long> implements Serializable{

    private Boolean isDefault;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private Boolean isValid;

    private RepoType repoType = RepoType.Normal;

    @Transient
    private Long number;

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public RepoType getRepoType() {
        return repoType;
    }

    public Repo setRepoType(RepoType repoType) {
        this.repoType = repoType;
        return this;
    }
}