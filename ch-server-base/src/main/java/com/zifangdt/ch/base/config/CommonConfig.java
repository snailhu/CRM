package com.zifangdt.ch.base.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zifangdt.ch.base.converter.*;
import com.zifangdt.ch.base.dto.ticket.IP;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.TimeZone;

/**
 * Created by 袁兵 on 2017/9/5.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
public class CommonConfig implements Jackson2ObjectMapperBuilderCustomizer {
    public CommonConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println("################CommonConfig");
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder
                .timeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                .indentOutput(true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .deserializers(new JsonDateDeserializer())
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .modulesToInstall(new SimpleModule()
                        .addDeserializer(Enum.class, new CustomEnumDeserializer())
                                .addSerializer(Enum.class, new CustomEnumSerializer())
                                .addSerializer(Boolean.class, new CustomBooleanSerializer())
                        .addSerializer(IP.class, new CustomIPSerializer())
                );
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    private class CustomPasswordEncoder extends Md5PasswordEncoder {
        @Override
        public String encodePassword(String rawPass, Object salt) {
            return super.encodePassword(super.encodePassword(rawPass, salt), salt);
        }
    }
}
