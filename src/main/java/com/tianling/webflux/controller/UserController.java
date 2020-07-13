package com.tianling.webflux.controller;

import com.tianling.webflux.entities.User;
import com.tianling.webflux.repositorys.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 16:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("")
    public Flux<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/stream/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll(){
        return this.userRepository.findAll();
    }

    @PostMapping("")
    public Mono<User> getAll(@Validated @RequestBody User user){
        user.setId(null);
        return this.userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id){

        return  this.userRepository.findById(id)
                .flatMap(user -> this.userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable int start,@PathVariable int end){
        return this.userRepository.findByAgeBetween(start,end);
    }
    @GetMapping("/age/old")
    public Flux<User> findOld(){
        return this.userRepository.oldUser();
    }
}
