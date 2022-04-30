package cn.moublog.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Slf4j
@Component
public class MyAspect {

  @Pointcut("execution(public * cn.moublog.*.TestController.testFunc(..))")
  public void pointCut() {}

  @Before("pointCut()")
  public void before() {
      log.info("MyAspect before ...");
  }

  @After("pointCut()")
  public void after() {
      log.info("MyAspect after ...");
  }

  @AfterReturning("pointCut()")
  public void afterReturning() {
      log.info("MyAspect after returning ...");
  }

  @AfterThrowing("pointCut()")
  public void afterThrowing() {
      log.info("MyAspect after throwing ...");
  }

  @Around("pointCut()")
  public void around(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("MyAspect around before ...");
      joinPoint.proceed();
      log.info("MyAspect around after ...");
  }

    public static void main(String[] args) throws NoSuchFieldException {
        Class<MyAspect> myAspectClass = MyAspect.class;
        Field field = myAspectClass.getField("1");
        field.setAccessible(true);
    }
}
