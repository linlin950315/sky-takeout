package com.sky.handler;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    // 处理username重复的异常信息 先搜cause： 找到异常的名字
    // SQLIntegrityConstraintViolationException
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            // return Result.error("Duplicate entry用户名已存在")
            String[] split = message.split(" ");
            String dupUsername = split[2];
            String msg = dupUsername + MessageConstant.ALREADY_EXISTING_USERNAME;
            return Result.error(msg);

        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }
}
