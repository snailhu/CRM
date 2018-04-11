package com.zifangdt.ch.base.bo;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by 袁兵 on 2017/9/27.
 */
public class BatchUpdateBo {
    @NotEmpty
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
