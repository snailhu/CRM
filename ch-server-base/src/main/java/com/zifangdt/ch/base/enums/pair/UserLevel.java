package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.uaa.User;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public enum UserLevel implements PairedEnum {
    EMPLOYEE(1, "职员"), HEAD(2, "负责人"), BOSS(3, "boss");

    final int intVerifier;
    final String name;

    UserLevel(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public static String getName(User user) {
        if (user.getIsBoss() != null && user.getIsBoss() == Constants.YES) {
            return BOSS.getName();
        } else if (user.getIsHead() != null && user.getIsHead() == Constants.YES) {
            return HEAD.getName();
        } else {
            return EMPLOYEE.getName();
        }
    }

    public static UserLevel of(String levelName) {
        for (UserLevel u : values()) {
            if (u.getName().equals(levelName)) {
                return u;
            }
        }
        throw new IllegalArgumentException();
    }
}
