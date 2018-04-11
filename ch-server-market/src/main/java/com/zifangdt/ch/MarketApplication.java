package com.zifangdt.ch;

import com.zifangdt.ch.base.config.FeignConfig;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
public class MarketApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MarketApplication.class, args);
		new SpringApplicationBuilder(MarketApplication.class).web(true).run(args);
	}
}

