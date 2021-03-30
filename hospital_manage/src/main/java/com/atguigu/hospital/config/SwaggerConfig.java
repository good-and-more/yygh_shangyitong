package com.atguigu.hospital.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2配置信息
 * @author qy
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

//    @Bean
//    public Docket webApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("webApi")
//                .apiInfo(webApiInfo())
//                .select()
//                //过滤掉admin路径下的所有页面
//                .paths(Predicates.and(PathSelectors.regex("/P2P/.*")))
//                //过滤掉所有error或error.*页面
//                //.paths(Predicates.not(PathSelectors.regex("/error.*")))
//                .build();
//    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("webApi")
                .enable(true)
                .apiInfo(webApiInfo())
                .select()
                //这里指定Controller扫描包路径
                // RequestHandlerSelectors.basePackage("com.github.xiaoymin.knife4j.controller")是指扫描该包下的控制器，改成扫描所有使用Api注解的控制器
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("医院管理网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .version("1.0")
                .contact(new Contact("qy", "http://atguigu.com", "55317332@qq.com"))
                .build();
    }
}
