package org.example.spring.tx.dao;

import org.example.spring.tx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addMoney(User user) {
        String sql ="update account set money=money+?  where user=? ";
        int i = jdbcTemplate.update(sql,user.getMoney(),user.getUser());
        System.out.println("add :"+i);
    }

    @Override
    public void reduceMoney(User user) {
        String sql ="update account set money=money-?  where user=? ";
        int i = jdbcTemplate.update(sql,user.getMoney(),user.getUser());
        System.out.println("reduce :"+i);
    }
}
