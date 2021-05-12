package org.example.springboot;

import org.example.springboot.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://127.0.0.1:58582");
        String id = "2";
        User userResult = webClient.get().uri("/user/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(User.class).block();
        System.out.println(userResult.toString());
        Flux<User> users = webClient.get().uri("/user").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(User.class);
        users.map(stu->stu.getName()).buffer().doOnNext(System.out::println).blockFirst();
    }
}
