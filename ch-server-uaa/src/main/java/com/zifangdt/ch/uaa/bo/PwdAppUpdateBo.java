package com.zifangdt.ch.uaa.bo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 袁兵 on 2017/11/3.
 */
public class PwdAppUpdateBo {
    @NotBlank
    private String oldPwd;
    @NotBlank
    private String newPwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
