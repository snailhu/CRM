package com.zifangdt.ch.gateway.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/8/28.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String username;
    private Long userId;
    private String name;
    private String serverName;

    public JwtAuthenticationToken(Long userId, String username, String name, Collection<? extends GrantedAuthority> authorities, String serverName) {
        super(authorities);
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    public static JwtAuthenticationToken of(Claims claims) {
        String username = claims.getSubject();

        Long userId = Long.parseLong(claims.get("userId").toString());
        String name = claims.get("userName").toString();
        String serverName = claims.get("serverName").toString();

        Collection<GrantedAuthority> authorities =
                Arrays.stream(String.valueOf(claims.get("authorities")).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new JwtAuthenticationToken(userId, username, name, authorities, serverName);
    }

    @Override
    public String getName() {
        return name;
    }
}
