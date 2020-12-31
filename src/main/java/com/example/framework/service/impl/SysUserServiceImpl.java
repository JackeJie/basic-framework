package com.example.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.framework.common.ApiCode;
import com.example.framework.common.CommonConstant;
import com.example.framework.common.exception.MyException;
import com.example.framework.common.jwt.JWTUtil;
import com.example.framework.common.jwt.JwtProperties;
import com.example.framework.common.mybatis.page.PageInfo;
import com.example.framework.common.redis.RedisParameter;
import com.example.framework.common.response.ApiResult;
import com.example.framework.entity.SysUser;
import com.example.framework.mapper.SysUserMapper;
import com.example.framework.parameter.LoginParam;
import com.example.framework.parameter.UserPageParam;
import com.example.framework.parameter.po.UserPo;
import com.example.framework.service.ISysUserService;
import com.example.framework.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author wangjie
 * @since 2020-12-27
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ApiResult login(LoginParam loginParam) {
        String username = loginParam.getUsername();

        SysUser user = getSysUserByUsername(username);

        if (user == null) {
            log.error("登录失败,loginParam:{}", loginParam);
            throw new MyException(ApiCode.ERROR.getCode(), "未查询到该用户信息");
        }

        if (user.getState().equals(CommonConstant.STATUS_ONE)) {
            log.error("账号已被禁用,loginParam:{}", loginParam);
            throw new MyException(ApiCode.ERROR.getCode(), "账号已被禁用");
        }

        String encryptPassword = PasswordUtil.encrypt(loginParam.getPassword(), user.getSalt());
        if (!encryptPassword.equals(user.getPassword())) {
            throw new MyException(ApiCode.ERROR.getCode(), "密码错误");
        }

        UserPo userPo = UserPo.builder()
                .createTime(user.getCreateTime())
                .userId(user.getId())
                .userName(user.getUsername())
                .userPhone(user.getPhone())
                .nickname(user.getNickname())
                .build();


        // 生成token字符串并返回
        Long expireSecond = JwtProperties.JWT_EXPIRE_SECOND;
        String token = JWTUtil.generateToken(username, user.getId() + "", null, Duration.ofSeconds(expireSecond));
        log.debug("token:{}", token);

        userPo.setToken(token);
        log.debug("登录成功,username:{}", username);
        // 将token放置到redis中
        redisTemplate.opsForValue().set(String.format(RedisParameter.LOGIN_TOKEN,token),user.getId(),120L,TimeUnit.SECONDS);

        return ApiResult.ok(userPo);
    }

    @Override
    public SysUser getSysUserByUsername(String username) {
        SysUser sysUser = new SysUser().setNickname(username).setDeleted(CommonConstant.DELETE_FLAG_F);
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>(sysUser));
    }

    @Override
    public ApiResult queryPage(UserPageParam userPageParam) {
        // 构建查询条件
        QueryWrapper wrapper = new QueryWrapper();

        // 设置分页参数
        IPage page = new PageInfo<>(userPageParam);

        IPage<SysUser> userIPage = sysUserMapper.queryPage( wrapper,page);
        return ApiResult.ok(userIPage);
    }
}
