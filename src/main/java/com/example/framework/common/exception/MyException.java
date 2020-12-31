package com.example.framework.common.exception;

import com.example.framework.common.ApiCode;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName MyException
 * @date 2020/12/2718:55
 * @description: 自定义异常处理
 */
public class MyException extends RuntimeException {

    private Integer code;
    private String msg;

    public MyException(){
        super();
    }

    public MyException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

    public MyException(ApiCode apiCode){
        super(apiCode.getMessage());
        this.code = apiCode.getCode();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
