package com.zifangdt.ch.base.bo;

/**
 * Created by 袁兵 on 2017/9/11.
 */
public class KeyValuePair {
    private Long key;
    private String value;

    public KeyValuePair() {
    }

    public KeyValuePair(Long key) {
        this.key = key;
    }

    public KeyValuePair(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
