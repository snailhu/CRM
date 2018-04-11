package com.zifangdt.ch.base.config;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.converter.CustomStringToEnumConverterFactory;
import com.zifangdt.ch.base.web.FetchHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        System.out.println("################WebConfig");
    }

    @Autowired
    private FetchHeaderInterceptor fetchHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fetchHeaderInterceptor);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(Constants.FILE_MAX_SIZE);
        factory.setMaxRequestSize(Constants.FILE_TOTAL_SIZE);
        factory.setLocation(System.getProperty("java.io.tmpdir"));
        return factory.createMultipartConfig();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addConverterFactory(new CustomStringToEnumConverterFactory());
    }

}
