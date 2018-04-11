package com.zifangdt.ch.base.dto.setting;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.ConfigType;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by 袁兵 on 2018/1/8.
 */
@Table(name = "cfg_item")
public class ConfigItem extends BaseEntity<Long> {
    private ConfigType type;

    private BaseConfigDetail detail;

    @Transient
    private Boolean used;

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public BaseConfigDetail getDetail() {
        return detail;
    }

    public void setDetail(BaseConfigDetail detail) {
        this.detail = detail;
    }

    public ConfigItem(ConfigType type, BaseConfigDetail detail) {
        this.type = type;
        this.detail = detail;
    }

    public ConfigItem() {
    }
}
