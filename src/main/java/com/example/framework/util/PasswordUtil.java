package com.example.framework.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author wangjie
 * @version 1.0
 * @ClassName PasswordUtil
 * @date 2020/9/817:56
 */
public class PasswordUtil {

    /**
     * 密码加盐，再加密
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String encrypt(String pwd, String salt) {
        if (StringUtils.isBlank(pwd)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("盐值不能为空");
        }
        return DigestUtils.sha256Hex(pwd + salt);
    }

}
