package com.atguigu.yygh.common.exception;

import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//如果程序出现异常就会执行该方法。原理？
    @ResponseBody//将结果用json输出
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)//如果程序出现异常就会执行该方法。原理？
    @ResponseBody//将结果用json输出
    public Result error(YyghException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
