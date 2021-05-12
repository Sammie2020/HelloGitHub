package org.example.spring;

import org.example.spring.aspectj.Book;
import org.example.spring.aspectj.User;
import org.example.spring.config.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test3 {
    @Test
    public void TestAspectJ(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("bean13.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = context.getBean("user", User.class);
        user.add();
    }
    @Test
    public void TestAspectJ2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean14.xml");
        Book book = context.getBean("book", Book.class);
        book.buy();
    }
}
