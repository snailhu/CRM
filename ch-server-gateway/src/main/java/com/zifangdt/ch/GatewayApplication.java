package com.zifangdt.ch;

import com.zifangdt.ch.base.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@ComponentScan(excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.zifangdt.ch.base..*")
//})
//@Import(CommonConfig.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
