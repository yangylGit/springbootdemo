package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LogAspect {
    /**
     * 切入点表达式，拦截com.example.service包下的所有方法
     */
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void asc1(){

    }

    /**
     * 切入点表达式，拦截方法上有@Auth注解的所有方法
     */
    @Pointcut("@annotation(com.example.annotation.Auth)")
    public void auth(){

    }

    /**
     * 前置通知
     * @param jp
     */
    @Before(value = "auth()")
    public void before(JoinPoint jp){
        String name = jp.getSignature().getName();
        System.out.println(name + "方法开始执行...");
    }

    /**
     * 后置通知
     * @param jp
     */
    @After(value = "auth()")
    public void after(JoinPoint jp){
        String name = jp.getSignature().getName();
        System.out.println(name + "方法执行结束...");
    }

    /**
     * 返回通知
     * @param jp
     * @param result
     */
    @AfterReturning(value = "auth()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        String name = jp.getSignature().getName();
        System.out.println(name + "方法返回值为：" + result);
    }
    /**
     * 异常通知
     * @param jp
     * @param e
     */
    @AfterThrowing(value = "auth()", throwing = "e")
    public void afterThrowing(JoinPoint jp, Exception e) {
        String name = jp.getSignature().getName();
        System.out.println(name + "方法抛异常了，异常是：" + e.getMessage());
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("auth()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().getName();
        // 统计方法执行时间
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println(name + "方法执行时间为：" + (end - start) + " ms");
        return result;
    }
}
