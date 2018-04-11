package com.zifangdt.ch.base.dto.uaa;

import com.zifangdt.ch.base.dto.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 袁兵 on 2018/2/2.
 */
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 8672148863791523423L;

    private String name;
    private Long parentId;
    private String sn;

    @Transient
    private List<Permission> permissions;
    @Transient
    private List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Menu(Long id, String name, String sn, Long parentId) {
        super.setId(id);
        this.name = name;
        this.sn = sn;
        this.parentId = parentId;
    }

    public Menu(Long id, String name, String sn) {
        this(id, name, sn, null);
    }

    public Menu() {
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
