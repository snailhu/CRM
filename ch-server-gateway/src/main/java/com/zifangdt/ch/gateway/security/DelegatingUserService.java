package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.dto.uaa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DelegatingUserService implements UserDetailsService {

    @Autowired
    private UaaServerApi uaaServerApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        user = uaaServerApi.getDetail(username, "username");
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<Permission> permissions = uaaServerApi.findPermissionsByUserId(user.getId());
        return new DelegatingUser(user, permissions);
    }

}
