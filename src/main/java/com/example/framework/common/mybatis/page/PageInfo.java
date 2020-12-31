package com.example.framework.common.mybatis.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName PageInfo
 * @date 2020/12/2718:17
 * @description: 自定义分页参数
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageInfo<T> extends Page<T> {

    /**
     * 分页参数
     */
    private BasePageParam pageParam;


    public  PageInfo(){
    }

    /**
     *  根据类的加载机制， 会先加载父类  然后加载子类
     * @param pageParam
     */
    public PageInfo(BasePageParam pageParam){
        super(pageParam.getPageIndex(),pageParam.getPageSize());
    }

}
