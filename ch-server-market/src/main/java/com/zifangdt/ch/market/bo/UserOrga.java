package com.zifangdt.ch.market.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOrga {
    public Boolean isUser;
    public Long userId;
    public String username;
    public Long orgaId;
    public String organizationName;
}