package com.zifangdt.ch.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zifangdt.ch.base.web.Codes;
import com.zifangdt.ch.base.web.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * Created by 袁兵 on 2017/12/13.
 */
@Component
public class Handle404PostFilter extends ZuulFilter {
    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.getThrowable() == null;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        boolean empty = context.getZuulResponseHeaders().isEmpty()
                && context.getResponseDataStream() == null
                && context.getResponseBody() == null;
        if (empty) {
            HttpServletResponse response = context.getResponse();
            if (response != null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                try {
                    converter.write(RestResult.fail(Codes.SERVICE_UNAVAILABLE, "请求的服务不存在"), MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
