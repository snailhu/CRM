package com.zifangdt.ch.uaa.bo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 袁兵 on 2017/11/3.
 */
public class UserAppUpdateBo {
    @NotBlank
    private String name;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
