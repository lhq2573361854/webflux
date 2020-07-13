package com.tianling.webflux.frame.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 19:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MethodInfo {
    private String url;
    private HttpMethod method;
    private Map<String,Object>map;
    private Mono<?> body;
    private Class<?> bodyElementType;
    private boolean returnFlux;
    private Class<?> returnElementType;

}
