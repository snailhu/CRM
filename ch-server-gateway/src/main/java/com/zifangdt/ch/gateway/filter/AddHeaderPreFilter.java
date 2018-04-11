package com.zifangdt.ch.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.Tenant;
import com.zifangdt.ch.base.util.JsonUtil;
import com.zifangdt.ch.base.web.RestResult;
import com.zifangdt.ch.gateway.mapper.TenantMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 袁兵 on 2017/8/30.
 */
@Component
public class AddHeaderPreFilter extends ZuulFilter {

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        return request.getAttribute(Constants.HTTP_HEADER_USER_ID) != null;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.addZuulRequestHeader(Constants.HTTP_HEADER_USER_ID, request.getAttribute(Constants.HTTP_HEADER_USER_ID).toString());
        try {
            ctx.addZuulRequestHeader(Constants.HTTP_HEADER_USER_NAME, URLEncoder.encode(request.getAttribute(Constants.HTTP_HEADER_USER_NAME).toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        try {
            ctx.addZuulRequestHeader(Constants.HTTP_HEADER_USER_ACTUAL_NAME, URLEncoder.encode(request.getAttribute(Constants.HTTP_HEADER_USER_ACTUAL_NAME).toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        ctx.addZuulRequestHeader(Constants.HTTP_HEADER_USER_AUTHORITIES, request.getAttribute(Constants.HTTP_HEADER_USER_AUTHORITIES).toString());
        ctx.addZuulRequestHeader(Constants.HTTP_HEADER_REQUEST_URL, request.getAttribute(Constants.HTTP_HEADER_REQUEST_URL).toString());

        String tenantId = request.getHeader(Constants.HTTP_HEADER_TENANT_ID);
        if (!StringUtils.isEmpty(tenantId)) {
            Tenant tenant = tenantMapper.selectByPrimaryKey(tenantId);
            if (tenant == null) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(404);
                ctx.setResponseBody(JsonUtil.stringify(RestResult.fail("租户ID不存在")));
                ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
                return null;
            }
            ctx.addZuulRequestHeader(Constants.HTTP_HEADER_TENANT_ID, tenantId);
            ctx.addZuulRequestHeader(Constants.HTTP_HEADER_TENANT_DB_ADDRESS, tenant.getDbAddress());
        }
        return null;
    }
}
