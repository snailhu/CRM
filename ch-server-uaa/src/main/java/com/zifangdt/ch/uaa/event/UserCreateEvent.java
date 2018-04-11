package com.zifangdt.ch.uaa.event;

import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.event.EntityCreateEvent;

public class UserCreateEvent extends EntityCreateEvent<User>{
    public UserCreateEvent(Object source, User entity) {
        super(source, entity);
    }
}
