package com.zifangdt.ch.base.dto.setting;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  用于人员部门配置中存储人员、部门
 */
@Table(name = "cfg_user_orga")
public class UserOrga extends AuditEntity<Long> {

    private Long userId;

    private Long organizationId;

    private Boolean isOrganizationCharger;

    private Boolean isUser;

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
}