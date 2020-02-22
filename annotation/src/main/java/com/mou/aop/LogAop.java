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

    /*
     spring 的环绕通知和前置通知，后置通知有着很大的区别，主要有两个重要的区别：

    目标方法的调用由环绕通知决定，即你可以决定是否调用目标方法，而前置和后置通知   是不能决定的，他们只是在方法的调用前后执行通知而已，即目标方法肯定是要执行的。

    环绕通知可以控制返回对象，即你可以返回一个与目标对象完全不同的返回值，虽然这很危险，但是你却可以办到。而后置方法是无法办到的，因为他是在目标方法返回值后调用

    环绕通知必须返回joinPoint.proceed(),返回代理对象调用目标方法
     */

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
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
