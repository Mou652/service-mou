package com.mou.mybatis.annotation;

import cn.hutool.core.util.ReflectUtil;
import com.mou.mybatis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: mou
 * @date: 2021/12/12
 */
@Slf4j
@Aspect
@Component
public class AuthAspect {

    private static final String fieldName = "name";

    @Around("@annotation(com.mou.mybatis.annotation.Auth)")
    public Object position(ProceedingJoinPoint pjd) throws Throwable {
        MethodSignature signature = (MethodSignature) pjd.getSignature();
        Auth annotationAuth = signature.getMethod().getAnnotation(Auth.class);
        // 执行目标方法
        Object proceed = pjd.proceed();
        if (annotationAuth.type() == 0) {
            // 强转成User;
            List<User> userList = (List<User>) proceed;
            userList.parallelStream().forEach(user -> {
                // 利用反射隐藏目标值
                log.info("字段脱敏,字段:{},原始值:{}", fieldName, user.getName());
                ReflectUtil.setFieldValue(user, fieldName, "雍卓");
                log.info("字段脱敏,字段:{},原始值:{}", fieldName, "雍卓");
            });
        }
        return proceed;
    }

    public static void main(String[] args) {

    }

}
