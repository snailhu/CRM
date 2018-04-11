package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.web.Codes;
import com.zifangdt.ch.base.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 袁兵 on 2017/11/9.
 */
@Component
public class CustomEntryPoint extends Http403ForbiddenEntryPoint {

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        converter.write(RestResult.fail(Codes.ACCESS_DENIED, "未登录用户禁止访问"), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
    }
}
