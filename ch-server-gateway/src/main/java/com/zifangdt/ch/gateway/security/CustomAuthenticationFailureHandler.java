package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.web.Codes;
import com.zifangdt.ch.base.web.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.error("登录失败...{}", exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if(exception instanceof BadCredentialsException){
            converter.write(RestResult.fail(Codes.LOGIN_FAILED,"用户名或密码有误"), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
        }else{
            converter.write(RestResult.fail(Codes.UNKNOWN_ERROR,"Unauthorized"), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
        }
    }
}
