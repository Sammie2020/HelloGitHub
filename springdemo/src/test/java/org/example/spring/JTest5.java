package org.example.spring;

import org.example.spring.tx.entity.User;
import org.example.spring.tx.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:bean11.xml")
@SpringJUnitConfig(locations = "classpath:bean11.xml")
public class JTest5 {
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
