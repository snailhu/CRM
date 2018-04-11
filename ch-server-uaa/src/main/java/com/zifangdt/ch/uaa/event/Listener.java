package com.zifangdt.ch.uaa.event;

import com.zifangdt.ch.base.api.MessageServerApi;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @Autowired
    MessageServerApi messageServerApi;

    @Autowired
    UaaServerApi uaaServerApi;

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    //    @Async
    @EventListener
    public void handleUserCreateEvent(UserCreateEvent event) {
        User user = event.getEntity();
        try {
            messageServerApi.createUser(user);
        } catch (Exception e) {
            // fail silently
            LOGGER.error("create huanxin user fail, " + e);
        }
    }
}
