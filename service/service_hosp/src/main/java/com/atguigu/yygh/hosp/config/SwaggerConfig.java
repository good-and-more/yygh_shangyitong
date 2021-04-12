package com.atguigu.yygh.hosp.config;

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

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    private static final String VERSION = "1.0.0";
    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                //这里指定Controller扫描包路径
                // RequestHandlerSelectors.basePackage("com.github.xiaoymin.knife4j.controller")是指扫描该包下的控制器，改成扫描所有使用Api注解的控制器
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("service_hosp-API文档")
                .contact(new Contact("learn_and_think", "https://github.com/good-and-more/yygh_shangyitong", "yygh@atguigu.com"))
                .description("尚医通-接口文档测试")
                .version(VERSION)
                .build();
    }
}
