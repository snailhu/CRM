package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public class IdAndName {
    private Long id;
    private String name;

    public IdAndName() {
    }

    public IdAndName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static IdAndName fromOption(DetailOfOption option) {
        return new IdAndName(option.getId(), option.getName());
    }
}
