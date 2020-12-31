package com.example.framework.common.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName MybatisConfig
 * @date 2020/12/2718:23
 * @description: mybatis 配置类
 */
@Configuration
@MapperScan("com.example.framework.mapper")
public class MybatisConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
