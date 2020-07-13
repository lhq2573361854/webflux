package com.tianling.webflux.controller;

import com.tianling.webflux.frame.interfaces.IUser;
import com.tianling.webflux.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 20:49
 */
@RestController
@RequestMapping("/demo")
public class TestController {
    @Autowired
    private IUser iUser;

    @GetMapping("/")
    public Flux<User> demo(){
       return iUser.getAllUser();
    }
}
