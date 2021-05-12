package org.example.springboot.Handler;

import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.*;

public class UserHandler {
    private final UserService userService;
    public UserHandler(UserService userService){
        this.userService = userService;
    }
    public Mono<ServerResponse> getUserById(ServerRequest request){
        //获取id值
        int userId= Integer.valueOf(request.pathVariable("id"));
        //空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        //调用service方法
        Mono<User> userMono= this.userService.getUserById(userId);
        //把userMono进行转换返回，使用Reactor操作符flatMap
        return userMono.flatMap(person->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(person))).switchIfEmpty(notFound);
    }
    public Mono<ServerResponse> getAllUsers(ServerRequest request){
        //调用service方法
       Flux<User> users= this.userService.getAllUser();
        //把userMono进行转换返回
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users,User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request){
        //获取user对象
        Mono<User> userMono=request.bodyToMono(User.class);
        //调用service方法
        return ServerResponse.ok().build(this.userService.saveUserInfo(userMono));
    }
}
