package com.tianling.webflux.repositorys;

import com.tianling.webflux.entities.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 20:43
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    /**
     * 根据年龄范围查找用户
     * @param start
     * @param end
     * @return
     */
    Flux<User> findByAgeBetween(int start,int end);

    /**
     * 返回1-200的数据
     * @return
     */
    @Query("{age:{'$gte':1,'$lte':200}}")
    Flux<User> oldUser();
}
