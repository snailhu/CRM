package com.zifangdt.ch.base.dto.daily;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.dto.ClientAware;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/9/21.
 */
@Table(name = "biz_comment")
public class Comment extends BaseEntity<Long> implements Serializable, ClientAware {
    private static final long serialVersionUID = -8525714934588418239L;

    private Long dailyId;
    @NotBlank
    private String content;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long createId;
    private Date createTime;
    private Long parentId;
    @NamedProperty(target = JsonPropertyTarget.USER)
    @Transient
    private Long target;

    private Boolean fromApp;

    @Transient
    private Long likeCount;

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getFromApp() {
        return fromApp;
    }

    @Override
    public void setFromApp(Boolean fromApp) {
        this.fromApp = fromApp;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getDailyId() {
        return dailyId;
    }

    public void setDailyId(Long dailyId) {
        this.dailyId = dailyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
