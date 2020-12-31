package com.example.framework.common.exception;

import com.example.framework.common.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName GlobalException
 * @date 2020/12/2719:00
 * @description: 全局异常信息
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * @Author wangjie
     * @Description 自定义异常信息捕获
     * @Date 2020/9/9 10:58
     **/
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public ApiResult createMyException(MyException m) {
        return ApiResult.fail(m.getCode(), m.getMessage());
    }

}
