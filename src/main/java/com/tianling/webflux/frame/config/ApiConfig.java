package com.tianling.webflux.frame.config;

import com.tianling.webflux.frame.interfaces.ProxyCreator;
import com.tianling.webflux.frame.impl.ProxyCreatorImpl;
import com.tianling.webflux.frame.interfaces.IUser;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 8:14
 */
@Configuration
public class ApiConfig {
    @Bean
     FactoryBean<IUser> userApi(ProxyCreator p){
        return new  FactoryBean<IUser>(){

            @Override
            public IUser getObject() throws Exception {
                return (IUser) p.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUser.class;
            }
        };
    }
    @Bean
    ProxyCreator proxyCreatorImpl(){
        return new ProxyCreatorImpl();
    }
}
