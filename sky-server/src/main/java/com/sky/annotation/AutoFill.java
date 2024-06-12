package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sky.enumeration.OperationType;

/**
 * 1.自定义注解 用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 指定数据库操作类型 拦截的是 操作对公共字段有影响的方法
    // updateTimeUser createTimeUser是和update insert连用的
    // import com.sky.enumeration.OperationType里有insert和update
    OperationType value();
}
