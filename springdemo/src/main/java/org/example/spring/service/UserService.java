package org.example.spring.service;

import org.example.spring.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//注解里面value属性值可以省略不写
//默认值是类名称，首字母小写
//还可以用@Component、@Controller、@Repository,功能是一样的
@Service(value = "userService") //类似于<bean id="userService" class=""/>
public class UserService {

    /**
     * xml配置
    private IUserDao userDao;
    public void add(){
        userDao.add();
        System.out.println("service add ....");
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
     */
    @Value(value = "abc")
    private String name;
    //不需要set方法
//    @Autowired //根据类型注入
//    @Qualifier(value = "userDao")
    @Resource(name = "userDao1")  //包：javax.annotation.Resource，是属于java的注解
    private IUserDao userDao;
    public void add(){
        System.out.println(name);
        userDao.add();
        System.out.println("service add ....");

    }

}
