package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 袁兵 on 2017/9/8.
 */
@Table(name = "com_division")
public class Division extends AuditEntity<String> implements Serializable{
    private static final long serialVersionUID = 1535778305758893620L;

    private String name;
    private String code;
    private Integer level;
    private String parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
