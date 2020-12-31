package com.example.framework.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName SwaggerConfig
 * @date 2020/12/2719:03
 * @description: swagger配置文件信息
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @Description:swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {
        //通过参数构造器为swagger添加对header参数的支持，如果不需要的话可以删掉
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDateTime.class,Date.class)
                .directModelSubstitute(LocalDateTime.class,String.class)
                .select()
                //扫描的swagger接口包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.framework"))
                .paths(PathSelectors.any())
                .build()
                //不需要添加全局参数时这一行可以删掉
                .globalOperationParameters(operationParameters())
                ;
    }
    /**
     * @Author wangjie
     * @Description 添加全局token头信息
     * @Date  2020/9/9 14:39
     **/
    private List<Parameter> operationParameters(){
        List<Parameter> parameters =new ArrayList<>();
        Parameter parameter = new ParameterBuilder()
                .name("token")
                .description("认证token")
                .required(false)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .build();
        parameters.add(parameter);
        return  parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("framework API文档")
                .description("springboot 基础框架")
                .termsOfServiceUrl("http://localhost:8888/framework/swagger-ui.html")
                .version("1.0")
                .build();
    }


}
