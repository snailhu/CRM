package com.zifangdt.ch;

import com.zifangdt.ch.base.config.FeignConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
public class SysSettingApplication
{
    public static void main( String[] args )
    {
        new SpringApplicationBuilder(SysSettingApplication.class).web(true).run(args);
    }
}
