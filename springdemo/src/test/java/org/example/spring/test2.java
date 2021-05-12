package org.example.spring;

import org.example.spring.config.SpringConfig;
import org.example.spring.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test2 {
    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        UserService  userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
    @Test
    public void testService2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService  userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
