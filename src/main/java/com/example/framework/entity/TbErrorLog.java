package com.example.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 错误日志信息
 * </p>
 *
 * @author wangjie
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_error_log")
@ApiModel(value="TbErrorLog对象", description="错误日志信息")
public class TbErrorLog extends Model<TbErrorLog> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "请求参数")
    private String excRequParam;

    @ApiModelProperty(value = "异常名称")
    private String excName;

    @ApiModelProperty(value = "异常信息")
    private String excMessage;

    @ApiModelProperty(value = "操作人id")
    private String operUserId;

    @ApiModelProperty(value = "操作员名称")
    private String operUserName;

    @ApiModelProperty(value = "操作方法")
    private String operMethod;

    @ApiModelProperty(value = "请求uri")
    private String operUri;

    @ApiModelProperty(value = "请求ip")
    private String operIp;

    @ApiModelProperty(value = "操作版本号")
    private String operVer;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime operCreateTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
