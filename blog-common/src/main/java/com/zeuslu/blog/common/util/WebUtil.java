package com.zeuslu.blog.common.util;


import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author lumingfan
 */
@Slf4j
public class WebUtil {
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    /**
     * 获取ServletRequestAttributes
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            return null;
        }
        return (ServletRequestAttributes) ra;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getResponse();
    }

    public static String getHeader(String headerName) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader(headerName);
    }

    public static String getMethod() {
        HttpServletRequest request = getRequest();;
        if (request == null) {
            return null;
        }
        return request.getMethod();
    }

    public static void setResponseHeader(String key, String value) {
        HttpServletResponse response = getResponse();
        if (response == null) {
            return;
        }
        response.setHeader(key, value);
    }
    public static String getCurrentUri() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getUri(request.getRequestURI());
    }

    public static String getUri(String url) {
        if (StrUtil.isEmpty(url)) {
            return StrUtil.EMPTY;
        }
        String uri = url;
        //uri中去掉 http:// 或者https
        if (uri.contains(HTTP_PROTOCOL)) {
            uri = uri.replace(HTTP_PROTOCOL, StrUtil.EMPTY);
        } else if (uri.contains(HTTPS_PROTOCOL)) {
            uri = uri.replace(HTTPS_PROTOCOL, StrUtil.EMPTY);
        }

        //uri 在url中的最后一个字符的序号+1
        int endIndex = uri.length();
        if (uri.contains("?")) {
            endIndex = uri.indexOf("?");
        }
        return uri.substring(uri.indexOf("/"), endIndex);
    }
}
