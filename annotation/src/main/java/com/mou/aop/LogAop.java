package com.mou.aop;

import com.mou.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: mou
 * @date: 2019-08-24
 */
@Aspect
@Slf4j
@Component
public class LogAop {

    /**
     * 切入点
     */
//    对类或者方法进行增强
//    @Pointcut("execution(...)")
    @Pointcut("@annotation(com.mou.annotation.Log)")
    public void logAspect() {

    }

    /**
     * Before前置通知
     */
    @Before("logAspect()")
    public void before() {
        log.info("前置通知:在方法之前执行");
    }

    /**
     * afterReturning后置通知
     *
     * @param value
     */
    @AfterReturning(value = "logAspect()", returning = "value")
    public void afterReturning(Object value) {
        log.info("后置通知:");
    }

    /**
     * after最终通知
     */
    @After("logAspect()")
    public void after() {
        log.info("最终通知:");
    }

    /**
     * afterThrowing异常通知
     *
     * @param e
     */
    @AfterThrowing(value = "logAspect()", throwing = "e")
    public void exception(Throwable e) {
        log.info("异常通知:在发送异常的时候执行");
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
