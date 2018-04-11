package com.zifangdt.ch.base.util.requestcache;

import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;

import java.util.concurrent.Callable;


public class RequestScopeCache extends AbstractValueAdaptingCache {

    private String name;

    /**
     * store的生命周期为request scope，每个request都创建一个store，request结束就回收store
     */
    private RequestScopeCacheStorage store;

    public RequestScopeCache(String name, RequestScopeCacheStorage store) {
        super(true);
        this.name = name;
//        store.getStore().put(name, new HashMap<>());
        this.store = store;
    }

    /**
     * 从缓存获取数据
     *
     * @param key
     * @return
     */
    @Override
    protected Object lookup(Object key) {
        return this.store.lookup(getName(), key);
    }

    /**
     * 获取缓存名称，缓存可以用名称做空间划分
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.store;
    }

    @Override
    public void put(Object key, Object value) {
        this.store.put(getName(), key, value == null ? NullValue.INSTANCE : value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        throw new RuntimeException("request scope cache no need to support this method");
    }

    @Override
    public void evict(Object key) {
        throw new RuntimeException("request scope cache no need to support this method");
    }

    @Override
    public void clear() {
        throw new RuntimeException("request scope cache no need to support this method");
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        throw new RuntimeException("request scope cache no need to support this method");
    }
}
