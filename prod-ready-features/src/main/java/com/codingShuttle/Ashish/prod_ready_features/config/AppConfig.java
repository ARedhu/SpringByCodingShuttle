package com.codingShuttle.Ashish.prod_ready_features.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    // The upper thing simply means create a bean/object of ModelMapper class by running the function "getModelMapper" when the application starts.

}
