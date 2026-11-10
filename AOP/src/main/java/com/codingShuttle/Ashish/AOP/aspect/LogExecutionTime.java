package com.codingShuttle.Ashish.AOP.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// We can define our own annotations as well.
@Target(ElementType.METHOD) // means where can the annotation be used. Here, only on methods.
@Retention(RetentionPolicy.RUNTIME) // means how long should the annotation exist. Here, the annotation is available at runtime.
public @interface LogExecutionTime {
}


// We can define the pointcuts seperately as well if we have to reuse it multiple times or if the pointcut is very complex. Remember we can apply &&, || things as well with points.
/*
@Pointcut("execution(* com.example.service.*.*(..))")
public void allServiceMethods() { // The method doesn't contain any implementation. It is basically a named representation of a pointcut expression. So, when we need to use this pointcut, we simply use this method name.
}

// Using the pointcut.
@Before("allServiceMethods()")
public void before() {
    System.out.println("Before");
}

@After("allServiceMethods()")
public void after() {
    System.out.println("After");
}

 */