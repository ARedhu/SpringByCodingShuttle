package com.codingShuttle.Ashish.SpringBootTesting;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration //  is used to define additional or customized beans specifically for your tests, without affecting your main application configuration.
public class TestContainerConfiguration {

    @Bean
    @ServiceConnection // This simply tells to springboot that the below method provides a service that my application needs.
    PostgreSQLContainer postgresContainer(){
        return new PostgreSQLContainer(
                DockerImageName.parse("postgres:17-alpine")
        ); // This will create a docker image with the given tag.
    }
}
