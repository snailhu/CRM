package com.zifangdt.ch.gateway.security;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.web.Codes;
import com.zifangdt.ch.base.web.RestResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = null;
        String header = request.getHeader(jwtProperties.getHeaderName());
        if (StringUtils.isEmpty(header) || !header.startsWith(jwtProperties.getHeaderPrefix())) {
            jwtToken = request.getParameter("t");
            if (StringUtils.isEmpty(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            jwtToken = header.substring(jwtProperties.getHeaderPrefix().length() + 1);
        }

        JwtAuthenticationToken jwtAuthenticationToken = null;
        LOGGER.info("尝试解析token..." + jwtToken);
        try {
            jwtAuthenticationToken = jwtTokenService.parseJwtToken(jwtToken);
            if (StringUtils.isNotEmpty(jwtAuthenticationToken.getServerName())
                    && !request.getServerName().equals(jwtAuthenticationToken.getServerName())) {
                LOGGER.error("token不属于当前域名:" + request.getServerName() + "≠" + jwtAuthenticationToken.getServerName());
                throw new WrongOperationException();
            }
        } catch (Exception e) {
            LOGGER.error("解析token失败..." + e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            converter.write(RestResult.fail(Codes.TOKEN_INVALID, "token不合法或者已失效"), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        request.setAttribute(Constants.HTTP_HEADER_USER_ID, jwtAuthenticationToken.getUserId());
        request.setAttribute(Constants.HTTP_HEADER_USER_NAME, jwtAuthenticationToken.getPrincipal());
        request.setAttribute(Constants.HTTP_HEADER_USER_ACTUAL_NAME, jwtAuthenticationToken.getName());
        request.setAttribute(Constants.HTTP_HEADER_USER_AUTHORITIES, jwtAuthenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        request.setAttribute(Constants.HTTP_HEADER_REQUEST_URL, request.getRequestURL().toString());

        LOGGER.info("解析token成功:{}@{},正在执行:{} {}", jwtAuthenticationToken.getPrincipal(), jwtAuthenticationToken.getServerName(), request.getMethod(), request.getRequestURL());
        filterChain.doFilter(request, response);
    }

}
