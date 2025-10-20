package com.javalin.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Slf4j
@Component
public class FirstAspect {


    /*
        execution (modifiers-pattern? ret-type-pattern
        declaring-type-pattern?name-pattern(param-pattern) throws
     */
    @Pointcut("execution(public * com.javalin.*.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod() "
//            +
//            "&& args(id) " +
//            "&& target(service) " +
//            "&& this(serviceProxy)"
    )
//    @Before("execution(public * com.javalin.spring.service.*Service.findById(*))")
    public void addLogging(JoinPoint joinPoint
//            ,
//                           Object id,
//                           Object service,
//                           Object serviceProxy
    ){
//        log.info("before invoked findById method in class {}, with id {}", service, id);

    }

    @AfterReturning(value = "anyFindByIdServiceMethod()" +
            "&& target(service) " +
            "&& args(id)", returning = "result")
    public void addLoggingAfterReturning(Object service, Object id, Object result){
        log.info("after returning - invoked findById method in class {}, with id {}, result {}", service, id, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod()" +
            "&& target(service) " +
            "&& args(id)", throwing = "ex")
    public void addLoggingAfterTrhowing(Object service, Object id, Throwable ex){
        log.info("after throwing - invoked findById method in class {}, with id {}, exception {}: {}", service, id, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod()" +
            "&& target(service) " +
            "&& args(id)")
    public void addLoggingFinally(Object service, Object id){
        log.info("after (finally) - invoked findById method in class {}, with id {}", service, id);
    }






















}
