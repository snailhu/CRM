package com.zifangdt.ch.base.dto.uaa;

import com.zifangdt.ch.base.dao.ManyToManyRelation;
import com.zifangdt.ch.base.dto.AuditEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 袁兵 on 2017/8/29.
 */
@Table(name = "sys_role")
public class Role extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -5324550530190991125L;

    @NotBlank
    private String name;
    private String description;
    private Integer deleted;

    @Transient
    @ManyToManyRelation(value = "sys_role_permission",targetKeyColumn = "permission_id")
    private List<Long> permissions;

    @Transient
    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Long> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
