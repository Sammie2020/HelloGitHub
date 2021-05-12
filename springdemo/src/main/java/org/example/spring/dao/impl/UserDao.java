package org.example.spring.dao.impl;

import org.example.spring.dao.IUserDao;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao1")
public class UserDao implements IUserDao {

    @Override
    public void add() {
        System.out.println("UserDao add 666");
    }
}
