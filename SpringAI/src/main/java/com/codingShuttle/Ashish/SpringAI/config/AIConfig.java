package com.codingShuttle.Ashish.SpringAI.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder.build();
    }
}

// ChatClient: It helps us to communicate with an AI model.
// ChatModel works under chatclient.
