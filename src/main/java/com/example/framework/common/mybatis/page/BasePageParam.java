package com.example.framework.common.mybatis.page;

import com.example.framework.common.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName BasePageParam
 * @date 2020/12/2718:10
 * @description: TODO
 */
@Data
@ApiModel("查询分页参数对象")
public abstract class BasePageParam implements Serializable {

    @ApiModelProperty(value = "页码,默认为1", example = "1")
    private Long pageIndex = CommonConstant.DEFAULT_PAGE_INDEX;

    @ApiModelProperty(value = "页大小,默认为10", example = "10")
    private Long pageSize = CommonConstant.DEFAULT_PAGE_SIZE;

    public void setPageIndex(Long pageIndex) {
        if (pageIndex == null || pageIndex <= 0) {
            this.pageIndex = CommonConstant.DEFAULT_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
        }
    }

    public void setPageSize(Long pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }


}
