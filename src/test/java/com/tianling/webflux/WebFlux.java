package com.tianling.webflux;

import com.tianling.webflux.entities.User;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import java.util.stream.Stream;


/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/10 10:25
 */

public class WebFlux {

    public static void main(String[] args) {
//        User user = new User("1", "abc", 23);
//        Stream.of(user).flatMap(item -> item.getName().chars().boxed())
//                .forEach(item -> System.out.println(Character.toChars(item)));
//        System.out.println(" ================ ");
//        Stream.of(user).map(item -> item.getName())
//                .forEach(item -> System.out.println(item));
        Consumer<Integer> lineHandler = System.out::println;
        lineHandler.accept(123);

    }


}
