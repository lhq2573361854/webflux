package com.tianling.webflux.frame.impl;

import com.tianling.webflux.frame.interfaces.RestHandler;
import com.tianling.webflux.frame.annotations.ApiServer;
import com.tianling.webflux.frame.domain.MethodInfo;
import com.tianling.webflux.frame.domain.ServerInfo;
import com.tianling.webflux.frame.interfaces.ProxyCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 8:19
 */
@Slf4j
public class ProxyCreatorImpl implements ProxyCreator {

    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy type : "+type);
        ServerInfo serverInfo = extractServerInfo(type);
        RestHandler restHandler = new WebClientRestHandler();
        restHandler.init(serverInfo);
        log.info("serverInfo : "+serverInfo);
        return  Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{type},new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodInfo methodInfo = extractMethodInfo(method,args);
                log.info("methodInfo : "+methodInfo);
                return restHandler.invokeRest(methodInfo);
            }
        });
    }

    private ServerInfo extractServerInfo(Class<?> type) {
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        ServerInfo serverInfo = new ServerInfo();
        log.info(annotation.value());
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }

    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof GetMapping){
                GetMapping getMapping = (GetMapping) annotation;
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }else if(annotation instanceof PostMapping){
                PostMapping getMapping = (PostMapping) annotation;
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }else if(annotation instanceof PutMapping){
                PutMapping getMapping = (PutMapping) annotation;
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }else if(annotation instanceof DeleteMapping){
                DeleteMapping getMapping = (DeleteMapping) annotation;
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        }
        Parameter[] parameters = method.getParameters();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        methodInfo.setMap(map);
        for (int i = 0; i < parameters.length; i++) {
            PathVariable annotation = parameters[i].getAnnotation(PathVariable.class);
            if (annotation != null) {
                map.put(annotation.value(),args[i]);
            }
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                methodInfo.setBody((Mono<?>)args[i]);
                Class<?> actualTypeArgument = (Class<?>)((ParameterizedType) parameters[i].getParameterizedType()).getActualTypeArguments()[0];
                methodInfo.setBodyElementType(actualTypeArgument);
            }
        }

        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        Type actualTypeArgument = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

        methodInfo.setReturnFlux(isFlux);
        methodInfo.setReturnElementType((Class<?>)actualTypeArgument);
        log.info(methodInfo.toString());
        return methodInfo;
    }
}
