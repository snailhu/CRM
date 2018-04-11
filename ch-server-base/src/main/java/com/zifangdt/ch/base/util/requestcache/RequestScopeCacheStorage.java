package com.zifangdt.ch.base.util.requestcache;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

/**
 * store会在每次request重置
 */
@Component
@RequestScope
public class RequestScopeCacheStorage {

    private final Map<String, Map<Object, Object>> store =  new HashMap<>();

    public Map<Object, Object> getCacheFor(String cacheName){
        if (getStore().get(cacheName) == null){
            getStore().put(cacheName, new HashMap<>());
            return getStore().get(cacheName);
        } else {
            return getStore().get(cacheName);
        }
    }

    public Map<String, Map<Object, Object>> getStore() {
        return this.store;
//        return requestStorage.getStore();
    }


    public Object lookup(String cacheName, Object key) {
        return getCacheFor(cacheName).get(key);
    }

    public void put(String cacheName, Object key, Object value) {
        getCacheFor(cacheName).put(key, value);
    }
}
