package org.example.spring;

import org.example.spring.config.TxConfig;
import org.example.spring.tx.entity.User;
import org.example.spring.tx.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test5 {
    @Test
    public void testTx(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        UserService userService = context.getBean("userServiceTx", UserService.class);
        //    Remitter汇款人  Payee收款人
        User Remitter = new User();
        Remitter.setMoney(100);
        Remitter.setUser("lucy");
        User  Payee = new User();
        Payee.setMoney(100);
        Payee.setUser("mary");
        userService.accountMoney(Remitter,Payee);
    }
    @Test
    public void testTx1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean15.xml");
        UserService userService = context.getBean("userServiceTx", UserService.class);
        //    Remitter汇款人  Payee收款人
        User Remitter = new User();
        Remitter.setMoney(100);
        Remitter.setUser("lucy");
        User  Payee = new User();
        Payee.setMoney(100);
        Payee.setUser("mary");
        userService.accountMoney(Remitter,Payee);
    }
    @Test
    public void testTx2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = context.getBean("userServiceTx", UserService.class);
        //    Remitter汇款人  Payee收款人
        User Remitter = new User();
        Remitter.setMoney(100);
        Remitter.setUser("lucy");
        User  Payee = new User();
        Payee.setMoney(100);
        Payee.setUser("mary");
        userService.accountMoney(Remitter,Payee);
    }
}
