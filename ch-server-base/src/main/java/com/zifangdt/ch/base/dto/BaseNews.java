package com.zifangdt.ch.base.dto;

import com.zifangdt.ch.base.converter.NameGenerator;
import com.zifangdt.ch.base.enums.NewsType;

import java.util.Date;

/**
 * Created by 袁兵 on 2017/9/20.
 */
public abstract class BaseNews extends BaseEntity<Long> {

    private String tag;
    private Date happenedAt;
    @NameGenerator
    private Long operator;
    private Long source;
    private String sourceName;

    protected abstract NewsType getType();

    public String getContent() {
        return getId() == null ? null : getType().getContentTemplate();
    }


    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getHappenedAt() {
        return happenedAt;
    }

    public void setHappenedAt(Date happenedAt) {
        this.happenedAt = happenedAt;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }


}
