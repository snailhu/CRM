package com.zifangdt.ch.base.config;

import com.zifangdt.ch.base.util.requestcache.RequestScopeCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport{


//    @Bean
//    public net.sf.ehcache.CacheManager ehCacheManager() {
//        CacheConfiguration cacheConfiguration = new CacheConfiguration();
//        cacheConfiguration.setName("publicData");
//        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
//        cacheConfiguration.setMaxEntriesLocalHeap(1000);
//        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
//        config.addCache(cacheConfiguration);
//        return net.sf.ehcache.CacheManager.newInstance(config);
//    }
//
//    @Bean(name = "ehCacheCacheManager")
//    @Override
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheManager());
//    }



    @Bean(name = "requestCacheManager")
    @Primary
    CacheManager getCacheManager(){
        CacheManager cacheManager = new RequestScopeCacheManager();
        return cacheManager;
    }
}
