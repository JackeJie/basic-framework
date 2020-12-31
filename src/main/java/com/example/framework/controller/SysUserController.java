package com.example.framework.controller;


import com.example.framework.common.aop.OperLog;
import com.example.framework.common.response.ApiResult;
import com.example.framework.parameter.LoginParam;
import com.example.framework.parameter.UserPageParam;
import com.example.framework.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author wangjie
 * @since 2020-12-27
 */
@RestController
@RequestMapping("/system/sys-user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;


    @PostMapping("/login")
    @ApiOperation(value = "登陆接口信息",notes = "用户登陆",tags = {"用户登陆信息"})
    @OperLog(operModul = "用户登陆",operDesc = "登陆",operType = "登陆操作")
    public ApiResult login(@Validated @RequestBody LoginParam loginParam){
        return sysUserService.login(loginParam);
    }


    @PostMapping("/list")
    @ApiOperation(value = "用户分页查询",notes = "用户分页查询",tags = {"用户分页查询"})
    @OperLog(operModul = "用户分页列表",operDesc = "查询",operType = "列表查询")
    public ApiResult list(@RequestBody UserPageParam userPageParam){
        return sysUserService.queryPage(userPageParam);
    }

}
