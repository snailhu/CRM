package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.util.CurrentUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 袁兵 on 2017/8/31.
 */
public class CustomFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(Constants.HTTP_HEADER_CALLED_BY,Constants.CALLED_BY_FEIGN);
        if (CurrentUser.getUserId() != null){
            template.header(Constants.HTTP_HEADER_USER_ID, CurrentUser.getUserId().toString());
            template.header(Constants.HTTP_HEADER_USER_NAME, CurrentUser.getUsername());
            try {
                template.header(Constants.HTTP_HEADER_USER_ACTUAL_NAME, URLEncoder.encode(CurrentUser.getName(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
            template.header(Constants.HTTP_HEADER_USER_AUTHORITIES, CurrentUser.getAuthorities());
        }
    }
}
