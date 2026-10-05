package com.codingShuttle.Ashish.SpringBootTesting.controllers;


import com.codingShuttle.Ashish.SpringBootTesting.TestContainerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

// Note: There is a possibility that we have multiple type of controllers, services, repositories. So, it is better to create a seperate class where we can put the common things and extend that class everywhere rather than writing the common things again and again in each file.
@SpringBootTest // Used for initializing spring application context for Integration testing.
@AutoConfigureWebTestClient(timeout = "10s") // Helps in Integration testing and making API calls. Timeout means, If this WebTestClient request doesn't complete within 10 seconds, consider it a failure. Remember timeout is not the time your API is allowed to take in production. It is the timeout for the test client's operations.
@Import(TestContainerConfiguration.class) // Helps to get the containerized database inside docker.
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;
}
