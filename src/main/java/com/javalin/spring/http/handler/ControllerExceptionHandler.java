package com.javalin.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.javalin.spring.http.controller")
public class ControllerExceptionHandler{

//    @ExceptionHandler(Exception.class)
//    public String handleExceptions(Exception e){
//        log.error("Failed to return response", e);
//        return "error/error500";
//    }
}
