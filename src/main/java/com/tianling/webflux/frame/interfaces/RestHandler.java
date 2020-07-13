package com.tianling.webflux.frame.interfaces;

import com.tianling.webflux.frame.domain.MethodInfo;
import com.tianling.webflux.frame.domain.ServerInfo;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 19:39
 */
public interface RestHandler {
    void init(ServerInfo serverInfo);

    Object invokeRest(MethodInfo methodInfo);
}
