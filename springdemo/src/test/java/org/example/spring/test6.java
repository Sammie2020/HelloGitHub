package org.example.spring;

import org.example.spring.entity.User;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;


public class test6 {
    //函数式风格创建对象，交给spring进行管理
    @Test
    public void test01(){
        //创建GenericApplicationContext对象
        GenericApplicationContext context = new GenericApplicationContext();
        //调用context方法对象注册
        context.refresh();
        context.registerBean(User.class,()->new User());
        //获取在spring注册的对象
        User user = (User) context.getBean("org.example.spring.entity.User");
        System.out.println(user);
        context.registerBean("user1",User.class,()->new User());
        //获取在spring注册的对象
        User user1 = (User) context.getBean("user1");
        System.out.println(user1);
    }
}
