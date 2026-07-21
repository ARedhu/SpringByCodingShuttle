package com.codingShuttle.Ashish.prod_ready_features.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {

    @Value("${employeeService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("employeeRestClient")
    RestClient getEmployeeServiceRestClient(){
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE) // This line tells the external server: "Hey, any data I send you in the body of my requests will be formatted as JSON."
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res)->{
                    throw new RuntimeException("Server not occured");
                })
                .build();
    }
}

// The @Qualifier annotation in Spring Boot is used to resolve ambiguity when multiple beans of the same type exist in the application context.  Without it, Spring cannot determine which bean to inject, resulting in a NoUniqueBeanDefinitionException.
/* When your application calls an API, the receiving server needs to know: "Who are you, and do you have permission to access this data?"
   That is what the Authorization header is for. It carries your app's "digital ID card" or credentials so the server can verify your identity before returning any data. */