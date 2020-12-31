package com.example.framework.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.framework.common.jwt.JWTUtil;
import com.example.framework.common.request.HttpServletRequestUtil;
import com.example.framework.entity.TbErrorLog;
import com.example.framework.entity.TbLog;
import com.example.framework.service.ITbErrorLogService;
import com.example.framework.service.ITbLogService;
import com.example.framework.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName WebControllerLogAop
 * @date 2020/12/2915:07
 * @description: 记录请求日志
 */
@Aspect
@Component
@Slf4j
public class WebControllerLogAop {

    private Logger logger = LoggerFactory.getLogger(WebControllerLogAop.class);


    @Autowired
    private ITbLogService tbLogService;

    @Autowired
    private ITbErrorLogService tbErrorLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.example.framework.common.aop.OperLog)")
    public void operLogPoinCut() {
    }


    /**
     * 定义切点
     */
    @Pointcut("execution(public * com.example.framework.controller..*.*(..))")
    public void wirteLog() {
    }


    @AfterReturning(value = "wirteLog()", returning = "keys")
    public void dofore(JoinPoint joinPoint, Object keys) {
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = HttpServletRequestUtil.getRequest();

        logger.info(JSONObject.toJSONString(joinPoint.getArgs()));

        TbLog tbLog = new TbLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog operLog = method.getAnnotation(OperLog.class);
            if (operLog != null) {
                // 操作模块
                String operModul = operLog.operModul();
                String operType = operLog.operType();
                String operDesc = operLog.operDesc();
                // 操作模块
                tbLog.setOperModul(operModul);
                // 操作类型
                tbLog.setOperType(operType);
                // 操作描述
                tbLog.setOperDesc(operDesc);
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            tbLog.setOperMethod(methodName);
            // 请求的参数
            String rtnMap = converMap(joinPoint.getArgs());
            //请求参数
            tbLog.setOperRequParam(rtnMap);
            // 返回参数
            tbLog.setOperRespParam(JSON.toJSONString(keys));
            // 如果请求方法是login 那么就
            if (request.getRequestURI().contains("/login")){
                //请求用户id
                tbLog.setOperUserId(queryToken(JSON.toJSONString(keys),"userId"));
                // 请求用户名
                tbLog.setOperUserName(queryToken(JSON.toJSONString(keys),"userName"));
            }else{
                //请求用户id
                tbLog.setOperUserId(JWTUtil.getUserID(request.getHeader("token")));
                // 请求用户名
                tbLog.setOperUserName(JWTUtil.getUsername(request.getHeader("token")));
            }
            // 请求ip
            tbLog.setOperIp(IpUtil.getIpAddr(request));
            //请求uri
            tbLog.setOperUri(request.getRequestURI());
            //创建时间
            tbLog.setOperCreateTime(LocalDateTime.now());
            tbLogService.save(tbLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "wirteLog()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = HttpServletRequestUtil.getRequest();

        TbErrorLog tbErrorLog =new TbErrorLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数 将参数所在的数组转换成json
            String rtnMap = converMap(joinPoint.getArgs());
            // 请求参数
            tbErrorLog.setExcRequParam(rtnMap);
            // 请求方法名
            tbErrorLog.setOperMethod(methodName);
            // 异常名称
            tbErrorLog.setExcName(e.getClass().getName());
            // 异常信息
            tbErrorLog.setExcMessage(stackTraceToString(e.getClass().getName(),e.getMessage(),e.getStackTrace()));
            // 操作人id
            tbErrorLog.setOperUserId(JWTUtil.getUserID(request.getHeader("token")));
            // 操作员名称
            tbErrorLog.setOperUserId(JWTUtil.getUsername(request.getHeader("token")));
            // 操作url
           tbErrorLog.setOperUri(request.getRequestURI());
            // 操作员ip
            tbErrorLog.setOperIp(IpUtil.getIpAddr(request));
            // 发生异常时间
            tbErrorLog.setOperCreateTime(LocalDateTime.now());
            tbErrorLogService.save(tbErrorLog);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    public String converMap(Object[] paramMap) throws IOException {
        if (paramMap == null) {
            return null;
        }
        // 去掉HttpServletRequest和HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : paramMap) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return  JSONObject.toJSONString(realArgs.get(0));
        } else {
            return  JSONObject.toJSONString(realArgs);
        }
    }
    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    /**
     * 需要做判断，如果是登陆操作 那么就需要在登陆之后拿到token进行解析操作
     */
    public String queryToken(String keys,String name){
        JSONObject jsonObject = JSONObject.parseObject(keys);
        if (jsonObject == null){
            return null;
        }
        JSONObject object = JSONObject.parseObject(jsonObject.get("data").toString());
        if (object == null){
            return null;
        }
        Object o = object.get(name);
        if (o == null){
            return null;
        }
        return String.valueOf(o);
    }




}
