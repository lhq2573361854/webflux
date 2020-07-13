package com.tianling.webflux.frame.impl;


import com.tianling.webflux.frame.domain.MethodInfo;
import com.tianling.webflux.frame.domain.ServerInfo;
import com.tianling.webflux.frame.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 20:19
 */
public class WebClientRestHandler implements RestHandler {
    private WebClient webClient;
    private WebClient.RequestBodySpec request;
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

   @Override
    public Object invokeRest(MethodInfo methodInfo) {
       WebClient.ResponseSpec retrieve = null;
       Object result  = null;
       request = this.webClient.method(methodInfo.getMethod())
           .uri(methodInfo.getUrl(), methodInfo.getMap())
           .accept(MediaType.APPLICATION_JSON);
       if (methodInfo.getBody() != null) {
           retrieve =  request.body(methodInfo.getBody(),methodInfo.getBodyElementType()).retrieve();
       }else
           retrieve =  request.retrieve();

       retrieve.onStatus(statues-> statues.value() == 404,
               response-> Mono.just(new RuntimeException("Not Found"))
       );

       if(methodInfo.isReturnFlux())
           result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
       else
           result = retrieve.bodyToMono(methodInfo.getReturnElementType());

       return result;
    }
}
