package com.tianling.webflux.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/12 22:23
 */
@ControllerAdvice
public class CheckBeanAdvice {

   @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e){
       return new  ResponseEntity<String>(toStr(e),HttpStatus.BAD_REQUEST);
    }

    private String toStr(WebExchangeBindException e){
        return e.getFieldErrors().stream().map(ex -> ex.getField()+":"+ex.getDefaultMessage())
                .reduce("",(e1,e2)->e1+"\n"+e2);
    }
}
