package com.zifangdt.ch.uaa.bo;

import com.zifangdt.ch.base.bo.BatchUpdateBo;

import java.util.List;

/**
 * Created by 袁兵 on 2017/10/17.
 */
public class BatchUpdatePermissionsBo extends BatchUpdateBo {
    private List<Long> permissions;

    public List<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Long> permissions) {
        this.permissions = permissions;
    }
}
