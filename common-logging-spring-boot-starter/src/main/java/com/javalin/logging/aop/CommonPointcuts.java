package com.javalin.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {
    /*
   @within - check annotation on the class level
    */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
    within - check class type name
     */
    @Pointcut("within(com.javalin.*.service.*Service)")
    public void isServiceLayer() {
    }
}
