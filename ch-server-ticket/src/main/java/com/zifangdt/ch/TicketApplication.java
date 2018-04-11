package com.zifangdt.ch;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.zifangdt.ch.base.config.FeignConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;


/**
 * 工单服务
 */
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
@Import(FdfsClientConfig.class)

public class TicketApplication
{
    public static void main( String[] args ) {
        new SpringApplicationBuilder(TicketApplication.class).web(true).run(args);
    }
}
