package org.example.spring.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class PersonProxy {
    @Before(value = "execution(* org.example.spring.aspectj.User.add(..))")
    public void before(){
        System.out.println("PersonProxy 前置通知before……");
    }
}
