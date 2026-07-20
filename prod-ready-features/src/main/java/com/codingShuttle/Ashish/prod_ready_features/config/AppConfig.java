package com.codingShuttle.Ashish.prod_ready_features.config;

import com.codingShuttle.Ashish.prod_ready_features.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl") // Step-1: EnableJpaAuditing in any of the configurations files. and Step-4: Creating bean.
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    // The upper thing simply means create a bean/object of ModelMapper class by running the function "getModelMapper" when the application starts.

    @Bean
    AuditorAware<String> getAuditorAwareImpl(){
        return new AuditorAwareImpl();
    }
}

/*
Question:
Hey, shouldn't this bean return "AuditorAwareImpl" but we in our code are returning "AuditorAware<String>" ?
Code: @Bean
AuditorAware<String> getAuditorAwareImpl(){
    return new AuditorAwareImpl();
}

Ans:
That part trips a lot of people up when they first see it! The short answer is: No, your current code is actually written 100% correctly.

What you are seeing here is a fundamental Java concept called Polymorphism (specifically, coding to an interface).

Here is exactly why it's written this way:

        1. Interface vs. Implementation
In your code, AuditorAware<String> is the Interface (the blueprint), and AuditorAwareImpl is the Concrete Class (the actual tool that implements the blueprint).

Because your class is defined as:

Java
public class AuditorAwareImpl implements AuditorAware<String>
Java treats AuditorAwareImpl as a valid type of AuditorAware<String>. Therefore, returning a new AuditorAwareImpl() inside a method that promises to return an AuditorAware<String> is perfectly legal and standard practice.

        2. Why do we declare the Interface as the return type?
Spring Boot's JPA Auditing mechanism doesn't actually know or care about your custom class name (AuditorAwareImpl). Under the hood, Spring only looks into its application context box and asks:

        "Hey, does anyone have a Bean of type AuditorAware registered?"

By declaring the return type as the interface (AuditorAware<String>), you make it explicitly clear to Spring what kind of feature bean you are providing.

💡 Could you change it to return AuditorAwareImpl?
Technically, yes! If you changed the method signature to this, the code would still compile:

Java
@Bean
public AuditorAwareImpl getAuditorAwareImpl() {
    return new AuditorAwareImpl();
}
However, keeping it as AuditorAware<String> is highly preferred. If you ever decide to change how your auditor works down the line (for example, switching from a hardcoded string to fetching it from Spring Security via a new class like SpringSecurityAuditorImpl), you won't have to change the return type in your configuration class. You would only have to swap out the return new ... statement.


 */

/*
We create Bean of AuditorAware here manually rather than @Component only when we are not allowed to modify the "AuditorAwareImpl" class and we need the bean of it. Else @Component is simple and preferred. But we can't use both at the same time. 

 */