package com.zifangdt.ch.base.util;

import com.zifangdt.ch.base.constant.Permissions;

import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 袁兵 on 2017/8/30.
 */
public class CurrentUser {
    private static class UserInfo {
        Long userId;
        String username;
        String name;
        Set<String> authorities;

        private UserInfo() {
        }

        private UserInfo(Long userId, String username, String name, String authorities) {
            this.userId = userId;
            this.username = username;
            this.name = name;
            this.authorities = new HashSet<>(Arrays.asList(authorities.split(",")));
        }
    }

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Long userId, String username, String name, String authorities) {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new UserInfo(userId, username, name, authorities));
        }
    }

    public static void clear() {
        THREAD_LOCAL.set(null);
        THREAD_LOCAL.remove();
    }

    @Nullable
    public static Long getUserId() {
        if (THREAD_LOCAL.get() == null) {
            return null;
        }
        return THREAD_LOCAL.get().userId;
    }

    public static String getUsername() {
        if (THREAD_LOCAL.get() == null) {
            return null;
        }
        return THREAD_LOCAL.get().username;
    }

    public static String getName() {
        if (THREAD_LOCAL.get() == null) {
            return null;
        }
        return THREAD_LOCAL.get().name;
    }

    public static Set<String> getAuthorities() {
        if (THREAD_LOCAL.get() == null) {
            return null;
        }
        return THREAD_LOCAL.get().authorities;
    }

    public static boolean permitted(String permission) {
        Set<String> authorities = getAuthorities();
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        return authorities.stream().anyMatch(a -> a.equals(Permissions.ADMIN) || a.equals(permission));
    }

    public static boolean permittedForCustomerData() {
        return permitted(Permissions.CUSTOMER_DATA);
    }

    public static boolean permittedForContractData() {
        return permitted(Permissions.CONTRACT_DATA);
    }

    public static boolean permittedForClueData() {
        return permitted(Permissions.CLUE_DATA);
    }

    public static boolean permittedForQuoteData() {
        return permitted(Permissions.QUOTE_DATA);
    }

    public static boolean permittedForProjectData() {
        return permitted(Permissions.PROJECT_DATA);
    }

}
