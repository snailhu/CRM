package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.dao.ManyToManyRelation;
import com.zifangdt.ch.base.dto.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2017/12/13.
 */
@Table(name = "com_announcement")
public class Announcement extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -3199657089206611407L;

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Long createId;
    private Date createTime;
    private String tag;
    @Transient
    @ManyToManyRelation(value = "com_announcement_receiver", targetKeyColumn = "receiver_id")
    private List<Long> receivers;

    @Transient
    private Date readTime;

    @Transient
    @NotEmpty
    private List<Long> range;

    public List<Long> getRange() {
        return range;
    }

    public void setRange(List<Long> range) {
        this.range = range;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Long> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Long> receivers) {
        this.receivers = receivers;
    }
}
