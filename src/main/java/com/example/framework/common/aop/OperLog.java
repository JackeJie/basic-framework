package com.example.framework.common.aop;

import java.lang.annotation.*;

/***
 * @author wangjie
 * @date 2020-12-29
 * 利用注解进行日志记录
 */
//注解放置的目标位置,METHOD是可注解在方法级别上
@Target(ElementType.METHOD)
//注解在哪个阶段执行
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    // 操作模块
    String operModul() default "";
    // 操作类型
    String operType() default "";
    // 操作说明
    String operDesc() default "";

}
