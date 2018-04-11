package com.zifangdt.ch.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@Component
public class JwtTokenService {

    @Autowired
    private JwtProperties jwtProperties;

    public static final String DEFAULT_PERMISSION = "DEFAULT_PERMISSION";

    public String createJwtToken(Authentication authentication, String serverName) {
        Claims claims = Jwts.claims()
                .setSubject(authentication.getName())
                .setExpiration(Date.from(LocalDateTime.now().plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date());

        String authorities = CollectionUtils.isEmpty(authentication.getAuthorities()) ?
                DEFAULT_PERMISSION :
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        claims.put("authorities", authorities);
        DelegatingUser delegatingUser = (DelegatingUser) authentication.getPrincipal();
        claims.put("userId", delegatingUser.getUserId());
        claims.put("userName", delegatingUser.getName());
        claims.put("serverName", serverName);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(HS512, jwtProperties.getDecodedSecret())
                .compact();
    }

    public JwtAuthenticationToken parseJwtToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getDecodedSecret())
                .parseClaimsJws(jwtToken)
                .getBody();

        return JwtAuthenticationToken.of(claims);
    }
}
