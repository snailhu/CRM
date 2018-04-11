package com.zifangdt.ch.base.bo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 袁兵 on 2018/1/17.
 */
public class NamedBo {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
