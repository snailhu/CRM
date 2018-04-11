package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/15.
 */
@Component
public class UserVerboseFetcher extends AbstractVerboseFetcher<Long, User> {

    @Autowired
    private UaaServerApi uaaServerApi;
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    protected List<User> doFetch(Collection<Long> ids) {
//        if (applicationName.startsWith("uaa-")) {
//            Object userService = ApplicationContextUtil.getBean("userService");
//            Class<?> targetClass = AopUtils.getTargetClass(userService);
//            return (List<User>) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetClass, "getListForInternal", List.class), userService, new ArrayList<>(ids));
//        }
        try {
            return uaaServerApi.getListForInternal(new ArrayList<>(ids));
        } catch (Exception e) {
            LOGGER.error("fuck error:" + ids);
            return new ArrayList<>();
        }

    }

    @Override
    public String nameOf(User verbose) {
        return verbose.getName();
    }
}
