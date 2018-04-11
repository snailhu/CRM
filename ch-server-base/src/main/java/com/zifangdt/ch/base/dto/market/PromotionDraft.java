package com.zifangdt.ch.base.dto.market;

import com.zifangdt.ch.base.dto.AuditEntity;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "promotion_draft")
public class PromotionDraft extends AuditEntity<Long> {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}