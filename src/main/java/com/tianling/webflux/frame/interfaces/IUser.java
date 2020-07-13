package com.tianling.webflux.frame.interfaces;

import com.tianling.webflux.frame.annotations.ApiServer;
import com.tianling.webflux.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 7:58
 */
@ApiServer("http://localhost:8080/user")
public interface IUser {
    @GetMapping("/")
    Flux<User> getAllUser();
}
