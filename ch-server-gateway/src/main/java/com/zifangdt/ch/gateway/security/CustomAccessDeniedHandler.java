package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.web.Codes;
import com.zifangdt.ch.base.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 袁兵 on 2017/9/1.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private MappingJackson2HttpMessageConverter converter;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        converter.write(RestResult.fail(Codes.ACCESS_DENIED,String.format("没有请求权限(%s %s)",request.getMethod(),request.getRequestURI())), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
    }
}
