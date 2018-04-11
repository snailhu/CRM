package com.zifangdt.ch.base.dto.customer;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.ActionType;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/25.
 */
@Table(name = "biz_customer_action")
public class CustomerAction extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -2955516339242739136L;

    @NotNull
    @NamedProperty
    private ActionType type;
    private String description;
    @VerboseProperty(target = JsonPropertyTarget.FILE)
    private List<String> attachments;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long createId;
    private Date createTime;
    private Long customerId;

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
