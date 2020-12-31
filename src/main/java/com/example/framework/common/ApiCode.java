package com.example.framework.common;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName ApiCode
 * @date 2020/12/2718:58
 * @description: TODO
 */
public enum ApiCode {
    /**
     * 操作成功
     **/
    SUCCESS(200, "操作成功"),
    ERROR(100, ""),
    /**
     * 操作失败
     **/
    FAIL(500, "操作失败"),


    //  token 相关错误信息
    JWTDECODE_EXCEPTION(5107, "Token解析异常"),
    NOT_FOUND_TOKEN(5108, "token参数获取失败"),
    TOEKN_EXPIRE_SECOND (5109,"token已过期"),
    ;

    private final int code;
    private final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
