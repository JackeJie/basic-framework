package com.example.framework.common;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName CommonConstant
 * @date 2020/12/2717:56
 * @description: TODO
 */
public interface CommonConstant {

    /**
     * JWT用户名
     */
    String JWT_USERNAME = "username";
    /**
     * JWT用户名
     */
    String JWT_USER_ID = "id";

    /**
     * 分页相关
     */
    Long DEFAULT_PAGE_INDEX = 1L;
    Long DEFAULT_PAGE_SIZE=10L;

    /**
     * 分页总行数名称
     */
    String PAGE_TOTAL_NAME = "total";

    /**
     * 分页数据列表名称
     */
    String PAGE_RECORDS_NAME = "records";

    /**
     * 分页当前页码名称
     */
    String PAGE_INDEX_NAME = "pageIndex";

    /**
     * 分页当前页大小名称
     */
    String PAGE_SIZE_NAME = "pageSize";


    /**
     *  账号状态 0 正常 1  禁用
     **/
    Integer STATUS_ZERO = 0;
    Integer STATUS_ONE = 1;

    /**
     * 删除状态  1 已删除 0 未删除
     */
    Integer DELETE_FLAG_T = 1;

    Integer DELETE_FLAG_F = 0;

}
