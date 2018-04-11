package com.zifangdt.ch.base.converter;

import com.zifangdt.ch.base.dto.uaa.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/11.
 */
public class SimpleUserInfoPostProcessor implements VerboseInfoPostProcessor<User> {
    @Override
    public List<User> postProcess(List<User> list) {
        return list.stream().map(user -> {
            User u = new User();
            u.setId(user.getId());
            u.setName(user.getName());
            return u;
        }).collect(Collectors.toList());
    }
}
