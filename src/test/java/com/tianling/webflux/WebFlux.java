package com.tianling.webflux;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Stream;


/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/10 10:25
 */
public class WebFlux {

    public static void main(String[] args) {
        String[] arr = {"admin","root"};
        Stream.of(arr).filter(name -> name.equalsIgnoreCase(" 3123")).findAny()
                .ifPresent(name -> System.out.println(name));

    }


}
