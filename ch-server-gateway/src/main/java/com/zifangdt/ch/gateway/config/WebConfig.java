package com.zifangdt.ch.gateway.config;

import com.zifangdt.ch.base.web.CustomErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 袁兵 on 2017/9/28.
 */
@Configuration("gatewayWebConfig")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public CustomErrorController customErrorController(ErrorAttributes errorAttributes) {
        return new CustomErrorController(errorAttributes);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT.name());
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE.name());

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(configurationSource);
        return new FilterRegistrationBean(corsFilter);
    }

}
