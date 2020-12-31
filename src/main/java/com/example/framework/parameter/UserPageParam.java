package com.example.framework.parameter;

import com.example.framework.common.mybatis.page.BasePageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName UserPageParam
 * @date 2020/12/2720:53
 * @description: TODO
 */
@Data
@ApiModel(value = "用户分页查询参数",description = "用户分页查询参数")
public class UserPageParam extends BasePageParam {
}
