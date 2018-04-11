package com.zifangdt.ch.base.dto.uaa;

import com.zifangdt.ch.base.dao.ManyToManyRelation;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.util.Hierarchical;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 袁兵 on 2017/8/29.
 */
@Table(name = "sys_organization")
//@JsonIgnoreProperties(value = "roles", allowGetters = true)
public class Organization extends AuditEntity<Long> implements Serializable, Hierarchical {
    private static final long serialVersionUID = 912269477428020557L;

    @NotBlank
    private String name;
    private Integer priority;
    private Long headId;
    private Integer deleted;
    private String description;
    @NotNull
    private Long parentId;

    @Transient
    private List<User> users;
    @Transient
    private List<Organization> children;
    @Transient
    private List<Object> mixed;
    @Transient
    @ManyToManyRelation(value = "sys_organization_role", targetKeyColumn = "role_id")
    private List<Long> roles;

    @Transient
    private String headName;
    @Transient
    private String parentName;

    @Transient
    private List<Role> roleList;

    public List<Object> getMixed() {
        return mixed;
    }

    public void setMixed(List<Object> mixed) {
        this.mixed = mixed;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
