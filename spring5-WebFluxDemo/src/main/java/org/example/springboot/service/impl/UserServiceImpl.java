package org.example.springboot.service.impl;

import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    //创建map集合存储数据,模拟数据库
    private final Map<Integer,User> users = new HashMap<>();

    public UserServiceImpl() {
        this.users.put(1,new User("lucy","f",20));
        this.users.put(2,new User("jack","m",25));
        this.users.put(3,new User("zoey","f",20));
    }

    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person->{
            //向Map集合里面放值
            int id = users.size()+1;
            users.put(id,person);
        }).then(Mono.empty());
    }
}
