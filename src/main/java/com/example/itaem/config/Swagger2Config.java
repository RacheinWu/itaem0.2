//package com.example.itaem.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
//@Configuration
//@EnableSwagger2
//@ConditionalOnExpression("${swagger.enable}")
//public class Swagger2Config {
//
//
//    @Bean
//    Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("测试模块")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.itaem.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("ITAEM 开发接口文档")
//                .description("开发人员：吴远健，林志鹏")
//                .termsOfServiceUrl("itaem.cn")
//                .contact(new Contact("后端开发","itaem.cn", "wuyuanjian@stu.gdou.edu.cn"))
//                .version("0.2")
//                .build();
//
//    }
//
//
//}
