package com.zifangdt.ch.base.util.requestcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class RequestScopeCacheManager extends AbstractCacheManager{

    @Autowired
    RequestScopeCacheStorage requestScopeCacheStorage;

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptySet();
    }

    protected Cache getMissingCache(String name){
        return new RequestScopeCache(name, requestScopeCacheStorage);
    }
}
