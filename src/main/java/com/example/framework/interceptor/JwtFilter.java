package com.example.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.framework.common.ApiCode;
import com.example.framework.common.jwt.JWTUtil;
import com.example.framework.common.jwt.JwtProperties;
import com.example.framework.common.redis.RedisParameter;
import com.example.framework.common.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName JwtFilter
 * @date 2020/12/2718:27
 * @description: 定义一个过滤器  进行对token校验
 * urlPatterns 是你要进行拦截的路径 可以进行多个路径拦截
 * filterName 跟你的类名相同  但首字母小写
 * 在启动类上需要加 @ServletComponentScan 这个注解  过滤器才能生效
 */
@Slf4j
@WebFilter(urlPatterns = {"/system/*"}, filterName = "jwtFilter")
public class JwtFilter implements Filter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化加载。。。。。。。。。。");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求头 和相应对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 获取请求方法
        String requestURI = request.getRequestURI();

        // 过滤登陆和登出接口
        if (requestURI.contains("/login") || requestURI.contains("/loginout")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 获取请求头信息
            String token = request.getHeader("token");
            log.info("请求携带的token信息:{}", token);
            // 如果请求头没有token信息 则直接返回
            if (token == null || token.isEmpty()) {
                createResponse(servletResponse, JSONObject.toJSONString(ApiResult.fail(ApiCode.ERROR.getCode(), "token不能为空")));
                return;
            }
            Object redisUtilsString = redisTemplate.opsForValue().get(String.format(RedisParameter.LOGIN_TOKEN,token));
            log.info("获取token信息{}",redisUtilsString);
            // 如果token为空 则判断已失效
            if (ObjectUtils.isEmpty(redisUtilsString)){
                log.info("token已过期");
                createResponse(servletResponse, JSONObject.toJSONString(ApiResult.fail(ApiCode.TOEKN_EXPIRE_SECOND.getCode(), ApiCode.TOEKN_EXPIRE_SECOND.getMessage())));
                return;
            }
            // 验证token
            if (!JWTUtil.verifyToken(token, JwtProperties.JWT_SALT)) {
                log.info("token无效");
                createResponse(servletResponse, JSONObject.toJSONString(ApiResult.fail(ApiCode.JWTDECODE_EXCEPTION.getCode(), ApiCode.JWTDECODE_EXCEPTION.getMessage())));
                return;
            }
            // token 有效时间验证
            if (JWTUtil.isExpired(token)) {
                log.info("token已过期");
                createResponse(servletResponse, JSONObject.toJSONString(ApiResult.fail(ApiCode.TOEKN_EXPIRE_SECOND.getCode(), ApiCode.TOEKN_EXPIRE_SECOND.getMessage())));
                return;
            }
            log.info("token filter过滤ok!");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        log.info("服务关闭，销毁成功。。。。。。。。。。");
    }


    /**
     * 构建响应对象信息
     *
     * @param servletResponse
     * @return
     */
    private void createResponse(ServletResponse servletResponse, String data) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            // 通过 PrintWriter 将 data 数据直接 print 回去
            writer.print(data);
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


}
