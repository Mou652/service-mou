package com.mou.shiro.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常类
 *
 * @author: mou
 * @date: 2019-08-17
 */
@ControllerAdvice
public class GlobalBaseExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e instanceof AuthorizationException) {
            return "未授权";
        }
        return "未知异常";
    }
}
