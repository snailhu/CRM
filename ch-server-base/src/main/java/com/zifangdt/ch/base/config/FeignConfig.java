package com.zifangdt.ch.base.config;

import com.zifangdt.ch.base.web.CustomFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new CustomFeignInterceptor();
    }
}
