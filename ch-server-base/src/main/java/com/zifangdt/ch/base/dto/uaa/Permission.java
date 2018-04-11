package com.zifangdt.ch.base.dto.uaa;

import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.Terminal;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 袁兵 on 2017/8/29.
 */
@Table(name = "sys_permission")
public class Permission extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 521593731898634434L;

    private String name;
    private String sn;
    private String url;
    private Long menuId;
    private Terminal terminal;

    public Permission(Long id) {
        super.setId(id);
    }
    public Permission() {
    }

    public Permission(String name, String sn, String url, Long menuId, Terminal terminal) {
        this.name = name;
        this.sn = sn;
        this.url = url;
        this.menuId = menuId;
        this.terminal = terminal;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
}
