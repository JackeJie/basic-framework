package com.example.framework.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName
 * @date 2020/9/817:38
 * @description: 登录参数
 */
@Data
@ApiModel(value = "登录参数",description = "用户登录参数")
public class LoginParam implements Serializable {


    @NotBlank(message = "请输入账号")
    @ApiModelProperty(value = "账号", example = "admin")
    private String username;

    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
}
