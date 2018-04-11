package com.zifangdt.ch.base.dto.market;

import java.util.Map;

public class Component {
    private String type;
    private Map<String, String> meta;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
}
