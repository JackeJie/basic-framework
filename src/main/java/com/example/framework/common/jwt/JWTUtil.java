package com.example.framework.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.framework.common.CommonConstant;
import com.example.framework.common.request.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName JWTUtil
 * @date 2020/12/2717:27
 * @description: token生成工具
 */
@Slf4j
@Component
public class JWTUtil {

    /**
     * 生成JWT Token
     *
     * @param username       用户名
     * @param salt           盐值
     * @param expireDuration 过期时间和单位
     * @return token
     */
    public static String generateToken(String username, String userId, String salt, Duration expireDuration) {
        try {
            if (StringUtils.isBlank(username)) {
                log.error("username不能为空");
                return null;
            }
            log.debug("username:{}", username);

            // 如果盐值为空，则使用默认值：666666
            if (StringUtils.isBlank(salt)) {
                salt = JwtProperties.JWT_SALT;
            }
            log.debug("salt:{}", salt);

            // 过期时间，单位：秒
            Long expireSecond;
            // 默认过期时间为1小时
            if (expireDuration == null) {
                expireSecond = JwtProperties.JWT_EXPIRE_SECOND;
            } else {
                expireSecond = expireDuration.getSeconds();
            }
            log.debug("expireSecond:{}", expireSecond);
            Date expireDate = DateUtils.addSeconds(new Date(), expireSecond.intValue());
            log.debug("expireDate:{}", expireDate);

            // 生成token
            Algorithm algorithm = Algorithm.HMAC256(salt);
            String token = JWT.create()
                    // 这里可以写多个  自已定义
                    .withClaim(CommonConstant.JWT_USERNAME, username)
                    .withClaim(CommonConstant.JWT_USER_ID, userId)
                    // jwt唯一id 可以添加随机数来做
                    .withJWTId(userId)
                    // 签发人
                    .withIssuer(JwtProperties.JWT_ISSUER)
                    // 主题
                    .withSubject(JwtProperties.JWT_SUBJECT)
                    // 签发的目标
                    .withAudience(JwtProperties.JWT_AUDIENCE)
                    // 签名时间
                    .withIssuedAt(new Date())
                    // token过期时间
                    .withExpiresAt(expireDate)
                    // 签名
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            log.error("generateToken exception", e);
        }
        return null;
    }

    /**
     * 验证token
     * @param token
     * @param salt
     * @return
     */
    public static boolean verifyToken(String token, String salt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    // 签发人
                    .withIssuer(JwtProperties.JWT_ISSUER)
                    // 主题
                    .withSubject(JwtProperties.JWT_SUBJECT)
                    // 签发的目标
                    .withAudience(JwtProperties.JWT_AUDIENCE)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("Verify Token Exception", e);
        }
        return false;
    }


    /**
     * 解析token，获取token数据
     *
     * @param token
     * @return
     */
    public static DecodedJWT getJwtInfo(String token) {
        return JWT.decode(token);
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public static Date getExpireDate(String token) {
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        return decodedJwt.getExpiresAt();
    }

    /**
     * 判断token是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        Date expireDate = getExpireDate(token);
        if (expireDate == null) {
            return true;
        }
        return expireDate.before(new Date());
    }


    /**
     * 获取用户名 这个地方根据你generateToken 中设置来使用的
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        String username = decodedJwt.getClaim(CommonConstant.JWT_USERNAME).asString();
        return username;
    }


    /**
     * 获取用户名 这个地方根据你generateToken 中设置来使用的
     *
     * @param token
     * @return
     */
    public static String getUserID(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        String userId = decodedJwt.getClaim(CommonConstant.JWT_USER_ID).asString();
        return userId;
    }


    /**
     * 从请求头或者请求参数中
     *
     * @return
     */
    public String getToken() {
        return getToken(HttpServletRequestUtil.getRequest());
    }



    /**
     * 从请求头或者请求参数中
     *
     * @param request
     * @return
     */
    public  String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求头中获取token
        String token = request.getHeader(JwtProperties.JWT_HEADER_NAME);
        if (StringUtils.isBlank(token)) {
            // 从请求参数中获取token
            token = request.getParameter(JwtProperties.JWT_HEADER_NAME);
        }
        return token;
    }

}
