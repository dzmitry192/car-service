package com.innowise.sivachenko.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.innowise.sivachenko.service.CarServiceImpl.*get*(..))")
    public void logGetMethods() {
    }

    @Before("logGetMethods()")
    public void logGetMethodExecution(JoinPoint joinPoint) {
        System.out.println("Executing method: " + joinPoint.getSignature().getName()
                + ";\n With args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("logGetMethods()")
    public void logGetMethodReturn(JoinPoint joinPoint) {
        System.out.println("Method " + joinPoint.getSignature().getName() + " execution was successful.");
    }

}
