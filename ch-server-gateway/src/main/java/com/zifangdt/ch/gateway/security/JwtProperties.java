package com.zifangdt.ch.gateway.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@ConfigurationProperties("jwt")
@Component
public class JwtProperties {
    private int expireInMinutes;

    private String secret;
    private byte[] decodedSecret;

    private String headerName;
    private String headerPrefix;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderPrefix() {
        return headerPrefix;
    }

    public void setHeaderPrefix(String headerPrefix) {
        this.headerPrefix = headerPrefix;
    }

    @PostConstruct
    public void init(){
        this.decodedSecret = Base64.getDecoder().decode(secret);
    }

    public byte[] getDecodedSecret() {
        return decodedSecret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpireInMinutes() {
        return expireInMinutes;
    }

    public void setExpireInMinutes(int expireInMinutes) {
        this.expireInMinutes = expireInMinutes;
    }
}
