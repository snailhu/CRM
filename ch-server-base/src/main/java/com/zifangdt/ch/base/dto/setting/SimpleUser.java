package com.zifangdt.ch.base.dto.setting;

import com.zifangdt.ch.base.dto.uaa.User;

public class SimpleUser {
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public SimpleUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SimpleUser setName(String name) {
        this.name = name;
        return this;
    }

    public static final SimpleUser make(User user) {
        SimpleUser su = new SimpleUser();
        su.setId(user.getId());
        su.setName(user.getName());
        return su;
    }
}
