package com.example.itaem.myAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 注入CurUser
 *  range -> controller
 */
@Target({ElementType.PARAMETER})//作用于参数上
@Retention(RetentionPolicy.RUNTIME)
public @interface CurUser {
}
