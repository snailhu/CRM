package com.zifangdt.ch.base.dto;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/3/27.
 */
@Table
public class Tenant extends BaseEntity<String> {
    private String name;
    private String dbAddress;
    private Date createTime;
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(String dbAddress) {
        this.dbAddress = dbAddress;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
