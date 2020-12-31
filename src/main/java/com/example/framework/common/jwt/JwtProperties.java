package com.example.framework.common.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName JwtProperties
 * @date 2020/12/2717:09
 * @description: jwt配置信息
 */
@Data
@Component
public class JwtProperties {

    public static String JWT_ISSUER;
    public  static String JWT_SUBJECT;
    public  static String JWT_AUDIENCE;
    public  static String JWT_SECRET;
    public  static Long JWT_EXPIRE_SECOND;
    public static String JWT_SALT;
    public static String JWT_HEADER_NAME;



    /**
     * 签发人
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * 主题
     */
    @Value("${jwt.subject}")
    private String subject;
    /**
     * 签发的目标
     */
    @Value("${jwt.audience}")
    private String audience;
    /**
     * 密码
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * JWT 默认过期时间，3600L，单位秒
     * token失效时间,默认1小时，60*60=3600
     */
    @Value("${jwt.expireSecond}")
    private Long expireSecond;
    /**
     * 盐值
     */
    @Value("${jwt.salt}")
    private String salt;

    /**
     * 盐值
     */
    @Value("${jwt.header}")
    private String header;

   @PostConstruct
    public void  init(){
       JWT_ISSUER = this.issuer;
       JWT_SUBJECT = this.subject;
       JWT_SECRET = this.secret;
       JWT_EXPIRE_SECOND = this.expireSecond;
       JWT_AUDIENCE =this.audience;
       JWT_SALT = this.salt;
       JWT_HEADER_NAME = this.header;
   }





}
