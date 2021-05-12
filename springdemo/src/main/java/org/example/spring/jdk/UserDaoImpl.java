package org.example.spring.jdk;

public class UserDaoImpl implements  UserDao{
    @Override
    public int add(int a, int b) {
        System.out.println("正在执行add方法");
        return a+b;
    }

    @Override
    public String update(String id) {
        System.out.println("正在执行update方法");
        return id;
    }
}
