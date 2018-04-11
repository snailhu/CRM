package com.zifangdt.ch.base.dto;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;

import java.util.Date;

/**
 * Created by 袁兵 on 2017/9/20.
 */
public class AuditEntity<T> extends BaseEntity<T> {
    private Date createTime;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long createId;
    private Date modifyTime;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long modifyId;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }
}
