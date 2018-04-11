package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.dto.uaa.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DelegatingUser implements UserDetails {

    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public DelegatingUser(User user, List<Permission> permissions) {
        this.user = user;
        this.authorities = permissions.stream().map(p -> new SimpleGrantedAuthority(p.getSn())).collect(Collectors.toList());
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return user.getName();
    }

}
