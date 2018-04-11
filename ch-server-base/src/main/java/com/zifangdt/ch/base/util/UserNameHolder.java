package com.zifangdt.ch.base.util;

import com.zifangdt.ch.base.dto.uaa.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 袁兵 on 2017/9/20.
 */
public class UserNameHolder {
    private static final ThreadLocal<Map<Long, User>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    public static Map<Long, User> map() {
        return THREAD_LOCAL.get();
    }

    public static void set(Map<Long, User> map) {
        THREAD_LOCAL.get().putAll(map);
    }

    public static Set<Long> getCachedIds() {
        return THREAD_LOCAL.get().keySet();
    }

    public static String getOrganName(Long userId) {
        User user = THREAD_LOCAL.get().get(userId);
        return user == null ? null : user.getOrganizationName();
    }

    public static String getName(Long userId) {
        User user = THREAD_LOCAL.get().get(userId);
        return user == null ? null : user.getName();
    }
}
