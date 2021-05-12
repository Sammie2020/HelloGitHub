package org.example.spring.tx.service;

import org.example.spring.tx.dao.UserDao;
import org.example.spring.tx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceTx")
//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT ,timeout = -1,readOnly = false,rollbackFor = ArithmeticException.class)
public class UserService {
    @Autowired
    private UserDao userDao;
    public void accountMoney(User Remitter ,User  Payee){
//       try {
           //1.开启事务
           //2.业务操作
           userDao.reduceMoney(Remitter);
           //模拟异常
           int i = 10 / 0;
           userDao.addMoney(Payee);
           //3.没有发生异常，提交事务

//       }catch (Exception e){
//           //4.出现异常，事务回滚
//
//       }
    }

}
