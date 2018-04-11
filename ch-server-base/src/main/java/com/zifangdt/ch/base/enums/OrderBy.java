package com.zifangdt.ch.base.enums;

import com.google.common.base.CaseFormat;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public enum OrderBy {

    createTime, modifyTime, startTime, updateTime;

    public String getColumnName() {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name());
    }
}
