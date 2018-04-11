package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.validation.IntegerEnumValue;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Table(name = "com_tag")
public class Tag extends AuditEntity<Long> implements Serializable{
    private static final long serialVersionUID = 3778407190571492994L;

    @NotBlank
    private String tag;
    @NotNull
    @IntegerEnumValue({1,2})
    private Integer typeId;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
