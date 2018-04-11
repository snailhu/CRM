package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dao.DynamicDataSource;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * Created by 袁兵 on 2017/8/31.
 */
@Component
public class FetchHeaderInterceptor extends HandlerInterceptorAdapter {
    private static final Log LOGGER = LogFactory.getLog(FetchHeaderInterceptor.class);
    @Autowired
    private DynamicDataSource dynamicDataSource;

    public FetchHeaderInterceptor() {
        System.out.println("################FetchHeaderInterceptor");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        LOGGER.debug(String.format("request[%s %s] from %s arrived,ready to fetch header...", request.getMethod(), request.getRequestURL(), WebUtil.getRequestIp()));
        LOGGER.debug("is from feign client?" + WebUtil.isFeignRequest());
        String userId = request.getHeader(Constants.HTTP_HEADER_USER_ID);
        String username = request.getHeader(Constants.HTTP_HEADER_USER_NAME);
        String name = request.getHeader(Constants.HTTP_HEADER_USER_ACTUAL_NAME);
        String authorities = request.getHeader(Constants.HTTP_HEADER_USER_AUTHORITIES);

        LOGGER.debug(String.format("headers info fetched successfully:userId=%s,username=%s,name=%s,authorities=%s", userId, username, name, authorities));
        LOGGER.debug("CurrentUser before set:" + CurrentUser.getUserId());
        if (StringUtils.isNotEmpty(userId) &&
                StringUtils.isNotEmpty(username) &&
                StringUtils.isNotEmpty(name) &&
                StringUtils.isNotEmpty(authorities)) {
            CurrentUser.set(Long.parseLong(userId), URLDecoder.decode(username, "UTF-8"), URLDecoder.decode(name, "UTF-8"), authorities);
        }

        String tenantId = request.getHeader(Constants.HTTP_HEADER_TENANT_ID);
        if (StringUtils.isNotEmpty(tenantId)) {
            dynamicDataSource.switchDataSource(
                    request.getHeader(Constants.HTTP_HEADER_TENANT_ID),
                    request.getHeader(Constants.HTTP_HEADER_TENANT_DB_ADDRESS)
            );
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        CurrentUser.clear();
        dynamicDataSource.clear();
    }

}
