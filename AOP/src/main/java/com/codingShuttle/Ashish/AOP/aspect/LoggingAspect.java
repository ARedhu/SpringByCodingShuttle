package com.codingShuttle.Ashish.AOP.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect // This bean contains AOP logic.
@Component // Create and manage this class as a Spring bean
public class LoggingAspect {

    // Apply this method before the execution of this method whose path is provide, it's return type can be anything "*" and it can take any type of arguments "(..)"
    @Before("execution(* com.codingShuttle.Ashish.AOP.service.PaymentService.makePayment(..))")
    public void logBefore() {
        System.out.println("Method started");
    }


// @Around advice is the most powerful and important advice in Spring AOP. @Around advice allows you to execute code both before and after the target method, and most importantly, it gives you control over whether the target method actually executes.
    @Around("execution(* com.codingShuttle.Ashish.AOP.service.PaymentService.makePayment(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Thread thread = new Thread();
        thread.sleep(2000);
        Object result = joinPoint.proceed(); // Continue execution and call the actual target method.

        long end = System.currentTimeMillis();

        System.out.println(
                joinPoint.getSignature().getName()
                        + " took "
                        + (end - start)
                        + " ms"
        );

        return result;
    }
}

// @Before is an advice. It asks what should I do ? In this case: @Before → execute my code BEFORE the target method.
// Inside of advice we have Pointcut. It simply mentions which method our code/aspect apply to.
