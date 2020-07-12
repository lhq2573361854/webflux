package com.tianling.webflux.components;

import com.tianling.webflux.entities.User;
import com.tianling.webflux.repositorys.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 23:01
 */
@Component
public class UserHandler {
    private UserRepository userRepository;
    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  Mono<ServerResponse> getAllUser(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.saveAll(userMono), User.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request){
        String id = request.pathVariable("id");

        return  this.userRepository.findById(id).flatMap(
                user-> this.userRepository.delete(user)
                .then(ServerResponse.ok().build()))

                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
