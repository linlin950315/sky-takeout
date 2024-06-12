package com.sky.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

//自定义切面 实现公共字段自动填充
@Aspect
@Component
@Slf4j // 记录日志

public class AutoFillAspect {

    // 定义切入点 哪些类的哪些方法
    @Pointcut("execution(* com.sky.mapper.*.*(..))&& @annotation(com.sky.annotation.AutoFill)") // mapper下所有的类和方法
    public void autoFillPointCut() {
    }

    // 前置通知 仅拦截对公共字段有影响的方法 即加入之前定义的autofill注解
    @Before("autoFillPointCut()")
    public void AutoFill(JoinPoint joinpoint) {
        // 连接点
        log.info("开始进行公共字段自动填充");
        // 1.获取当前被拦截的方法上 是insert还是update（即数据库操作类型）
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();// 方法签名对象
        AutoFill autofill = signature.getMethod().getAnnotation(AutoFill.class);// 获取方法上的注解对象
        OperationType operatopnType = autofill.value();// 获取数据库操作类型
        // 2.获取被拦截的方法参数 即实体对象
        Object[] args = joinpoint.getArgs();
        if (args == null || args.length == 0) {// 防止空指针 先判断args是否为空
            return;
        }
        Object entity = args[0];// 获取实体对象
        // 3.准备数据 为entity几个公共属性赋值
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        // 4.通过反射 根据不同的操作类型 为entity几个公共属性赋值
        if (operatopnType == OperationType.INSERT) {
            // 为4个公共字段赋值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,
                        LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,
                        Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,
                        LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,
                        Long.class);
                // 4.通过反射 为entity几个公共属性赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operatopnType == OperationType.UPDATE) {
            // 为2个公共字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,
                        LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,
                        Long.class);
                // 4.通过反射 为entity几个公共属性赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
