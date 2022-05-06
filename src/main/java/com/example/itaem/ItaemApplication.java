package com.example.itaem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.example.itaem.mapper")
//@EnableSwagger2
public class ItaemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItaemApplication.class, args);
    }

}
