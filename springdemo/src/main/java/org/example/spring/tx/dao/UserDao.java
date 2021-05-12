package org.example.spring.tx.dao;

import org.example.spring.tx.entity.User;

public interface UserDao {
     void addMoney(User user);
     void reduceMoney(User user);
}
