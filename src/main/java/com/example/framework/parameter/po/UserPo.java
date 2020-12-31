package com.example.framework.parameter.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName UserPo
 * @date 2020/9/2110:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="app 用户登录成功返回参数", description="")
public class UserPo implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "电话")
    private String userPhone;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("token")
    private String token;
}
