package com.example.itaem.config;

import com.example.itaem.interceptors.AuthenticationInterceptor;
import com.example.itaem.interceptors.SecurityInterceptor;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Autowired
    SecurityInterceptor securityInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/file/**").addResourceLocations("file:" + "E:/file/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + "/data/gitee/file/");
//        registry.addResourceHandler("/itaem/**").addResourceLocations(
//                "file:" + "E:/ITAEMresource/"); //HTML存储位置
        registry.addResourceHandler("/itaem/**").addResourceLocations(
                "file:" + "/data/gitee/static/"); //HTML存储位置
//        registry.addResourceHandler("/").addResourceLocations(
//                "file:" + "E:/ITAEMresource/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/view").addResourceLocations("E:/ITAEMresource/");
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //身份拦截：
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
//                .excludePathPatterns("/index.html")
                .excludePathPatterns("/itaem/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/register/**");
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
//
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver);
    }


}
