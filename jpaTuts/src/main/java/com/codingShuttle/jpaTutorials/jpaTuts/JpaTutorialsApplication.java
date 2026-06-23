package com.codingShuttle.jpaTutorials.jpaTuts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class JpaTutorialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaTutorialsApplication.class, args);
        System.out.println("Hello Ashish...");
    }

}