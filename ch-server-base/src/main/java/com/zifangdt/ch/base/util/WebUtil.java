package com.zifangdt.ch.base.util;

import com.zifangdt.ch.base.constant.Constants;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * Created by 袁兵 on 2017/10/27.
 */
public class WebUtil {
    public static boolean isFeignRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return false;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String calledBy = request.getHeader(Constants.HTTP_HEADER_CALLED_BY);
        return Constants.CALLED_BY_FEIGN.equals(calledBy);
    }

    public static String getRequestHeader(String attr) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(attr);
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static int getRequestPort() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getLocalPort();
    }

    public static String getRequestIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            try {
                InetAddress address = InetAddress.getLocalHost();
                ip = address.getHostAddress();
            } catch (Exception e) {
            }
        }
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getUserAgentInfo() {
        UserAgent userAgent = UserAgent.parseUserAgentString(getRequestHeader("User-Agent"));
        StringBuilder sb = new StringBuilder(userAgent.getOperatingSystem().getName());
        if (userAgent.getBrowser() != null) {
            sb.append(",").append(userAgent.getBrowser().getName());
        }
        if (userAgent.getBrowserVersion() != null) {
            sb.append(" ").append(userAgent.getBrowserVersion().getVersion());
        }
        return sb.toString();
    }

    public static boolean isFromApp() {
        UserAgent userAgent = UserAgent.parseUserAgentString(getRequestHeader("User-Agent"));
        DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
        return deviceType == DeviceType.MOBILE || deviceType == DeviceType.TABLET;
    }
}
