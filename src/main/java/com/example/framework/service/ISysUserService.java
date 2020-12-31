package com.example.framework.service;

import com.example.framework.common.response.ApiResult;
import com.example.framework.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.framework.parameter.LoginParam;
import com.example.framework.parameter.UserPageParam;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author wangjie
 * @since 2020-12-27
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 用户登陆
     * @param loginParam
     * @return
     */
    ApiResult login(LoginParam loginParam);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名信息
     * @return
     */
    SysUser getSysUserByUsername(String username);

    /**
     * 用户信息分页查询
     * @param userPageParam
     * @return
     */
    ApiResult queryPage(UserPageParam userPageParam);
}
