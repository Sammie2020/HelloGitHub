package org.example.spring.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(3)
public class UserProxy {
    //相同切入点抽取
    @Pointcut(value = "execution(* org.example.spring.aspectj.User.add(..))")
    public void pointdemo(){

    }
//    @Before(value = "execution(* org.example.spring.aspectj.User.add(..))")
    @Before(value = "pointdemo()")
    public void before(){
        System.out.println("UserProxy 前置通知before……");
    }

    @AfterThrowing(value ="execution(* org.example.spring.aspectj.User.add(..))" )
    public void afterThrowing(){
        System.out.println("异常通知AfterThrowing……");
    }

    @Around(value ="execution(* org.example.spring.aspectj.User.add(..))" )
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知around before……");
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知around after……");
    }
    @After(value ="execution(* org.example.spring.aspectj.User.add(..))" )
    public void after(){
        System.out.println("最终通知 After……");
    }
    @AfterReturning(value ="execution(* org.example.spring.aspectj.User.add(..))" )
    public void afterReturning(){
        System.out.println("后置通知 afterReturning……");
    }
}
