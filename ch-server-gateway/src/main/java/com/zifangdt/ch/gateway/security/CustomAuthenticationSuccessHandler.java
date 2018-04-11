package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.web.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("[{}]登录成功...", authentication.getName());
        String jwtToken = jwtTokenService.createJwtToken(authentication, request.getServerName());
        converter.write(RestResult.success(jwtToken), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
    }
}
