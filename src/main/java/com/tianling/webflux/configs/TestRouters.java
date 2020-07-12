package com.tianling.webflux.configs;

import com.tianling.webflux.components.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 23:25
 */
@Configuration
public class TestRouters {
    @Bean
    public RouterFunction<ServerResponse> testRouter(UserHandler userHandler) {
        return  RouterFunctions.nest(RequestPredicates.path("/test"),
                RouterFunctions
                .route(RequestPredicates.GET("/"), userHandler::getAllUser)
                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),userHandler::createUser)
                .andRoute(RequestPredicates.DELETE("/{id}"),userHandler::deleteUser)
        );

    }
}
