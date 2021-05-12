package org.example.spring;

import org.example.spring.autowire.Emp;
import org.example.spring.bean.Orders;
import org.example.spring.entity.*;
import org.example.spring.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class test {
    @Test
    public void testAdd(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        User user = context.getBean("user", User.class);
        System.out.println(user);
        user.add();

    }
    @Test
    public void testBook1(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        Book book2 = context.getBean("book", Book.class);
        System.out.println(book.getBookname());
        System.out.println(book2.getBookname());
        System.out.println(book); //org.example.spring.entity.Book@10dba097
        System.out.println(book2); //org.example.spring.entity.Book@10dba097
    }
    @Test
    public void testBook2(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:bean.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book2", Book.class);
        System.out.println(book.getBookname());
    }
    @Test
    public void testSD(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        //2.获取配置创建的对象
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    @Test
    public void testInner(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
        //2.获取配置创建的对象
        Employee emp = context.getBean("emp", Employee.class);
        System.out.println(emp.toString());
    }
    @Test
    public void testJoin(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        //2.获取配置创建的对象
        Employee emp = context.getBean("emp", Employee.class);
        System.out.println(emp.toString());
    }
    @Test
    public void testCollection(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        //2.获取配置创建的对象
        Student stu = context.getBean("student", Student.class);
        System.out.println(stu.toString());
    }
    @Test
    public void testCoursesList(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
        //2.获取配置创建的对象
        Student stu = context.getBean("student", Student.class);
        System.out.println(stu.toString());
    }
    @Test
    public void testUtilList(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        //2.获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        System.out.println(book.toString());
    }
    @Test
    public void testFactoryBean(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
        //2.获取配置创建的对象
        Course course = context.getBean("myBean", Course.class);
        System.out.println(course.toString());
    }

    @Test
    public void testBean(){
        //1.加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean9.xml");
        //2.获取配置创建的对象
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println("第四步：获取创建bean实例对象");
        System.out.println(orders);
        //手动让bean实例销毁,close()方法是applicationContext实现类ClassPathXmlApplicationContext的方法。
        ((ClassPathXmlApplicationContext)context).close();
    }


    @Test
    public void testAutoWire(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean10.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp.toString());
    }
}
