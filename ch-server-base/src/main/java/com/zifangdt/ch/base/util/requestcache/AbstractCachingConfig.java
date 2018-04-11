package com.zifangdt.ch.base.util.requestcache;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public abstract class AbstractCachingConfig {

    @Bean(name = "requestCacheManager")
    @Primary
    CacheManager getCacheManager(){
        CacheManager cacheManager = new RequestScopeCacheManager();
        return cacheManager;
    }
}