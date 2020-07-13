package com.tianling.webflux.frame.interfaces;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 8:16
 */
public interface ProxyCreator {
    /**
     * 创建代理类
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}
