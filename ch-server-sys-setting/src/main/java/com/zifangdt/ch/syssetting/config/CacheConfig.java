package com.zifangdt.ch.syssetting.config;

import com.zifangdt.ch.base.util.requestcache.AbstractCachingConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends AbstractCachingConfig{
}
