package org.example.spring;

import org.example.spring.tx.entity.User;
import org.example.spring.tx.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //单元测试框架
@ContextConfiguration("classpath:bean11.xml") //加载配置文件
public class JTest4 {
    @Autowired
    UserService userService;
    @Test
    public void test1(){
        User Remitter = new User();
        Remitter.setMoney(100);
        Remitter.setUser("lucy");
        User  Payee = new User();
        Payee.setMoney(100);
        Payee.setUser("mary");
        userService.accountMoney(Remitter,Payee);
    }
}
