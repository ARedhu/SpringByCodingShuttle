package com.codingShuttle.Ashish.SpringBootTesting.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// We made this route because elastic bean stack try to make a get request on this path for health check.
// Provides a health check endpoint that Elastic Beanstalk's
// load balancer can use to verify application health.
@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Ok");
    }
}
