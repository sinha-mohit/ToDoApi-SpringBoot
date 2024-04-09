package com.example.todoapispring.customAnnotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeMonitorAspect {
    @Around("@annotation(TimeMonitor)") // advice
    public Object logtime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis(); // start time of the code

        // execute the joint point
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long totalExecutionTime = end - start;

        System.out.println("Execution time of " + joinPoint.getSignature() + " : " + totalExecutionTime + " milliseconds");
        return result;
    }

    // Another way
    /*
    private long startTime;

    @Before("@annotation(TimeMonitor)")
    public void before(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @After("@annotation(TimeMonitor)")
    public void after(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time of " + joinPoint.getSignature() + " : " + (endTime - startTime) + " milliseconds");
    }
    */

}
