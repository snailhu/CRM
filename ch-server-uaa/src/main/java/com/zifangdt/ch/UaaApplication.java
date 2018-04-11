package com.zifangdt.ch;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.zifangdt.ch.base.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
@Import(FdfsClientConfig.class)
public class UaaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaApplication.class, args);
	}

}
