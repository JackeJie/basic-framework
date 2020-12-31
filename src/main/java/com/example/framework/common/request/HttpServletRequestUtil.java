package com.example.framework.common.request;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName HttpServletRequestUtil
 * @date 2020/12/2717:59
 * @description: httprequest请求工具类
 */
public class HttpServletRequestUtil {
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
