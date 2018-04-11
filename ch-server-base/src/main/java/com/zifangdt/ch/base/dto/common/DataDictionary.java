package com.zifangdt.ch.base.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.DataEnum;

import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 袁兵 on 2017/9/8.
 */
@Table(name = "com_data_dictionary")
@JsonPropertyOrder({"key", "value"})
@JsonIgnoreProperties(value = "isExclusive", allowGetters = true)
public class DataDictionary extends AuditEntity<Long> implements Serializable {

    private static final long serialVersionUID = -1004479616156150894L;

    private String value;
    @JsonIgnore
    private Integer type;
    private String description;

    private String exclusions;

    @JsonProperty(value = "key")
    @Override
    public Long getId() {
        return super.getId();
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsExclusive(){
        if (type != null && type == DataEnum.contractTypes.getType()){
            return !StringUtils.isEmpty(exclusions);
        }
        return null;
    }
}
