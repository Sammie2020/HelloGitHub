package org.example.spring.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        //创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler((Object proxy, Method method, Object[] args)
//                -> System.out.println("aaa")));
       UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(new UserDaoImpl()));
       int result = dao.add(1,2);
       System.out.println("result:"+result);

    }

}
//创建代理对象的代码
class UserDaoProxy implements InvocationHandler{
    //1.传递代理的实现类，通过构造函数
    private Object obj;
    public UserDaoProxy(Object obj){
        this.obj = obj;
    }

    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法执行前："+method.getName()+":"+ Arrays.toString(args));

        Object res = (int) method.invoke(obj,args);
        System.out.println("方法执行后:"+obj);
        return res;
    }
}
