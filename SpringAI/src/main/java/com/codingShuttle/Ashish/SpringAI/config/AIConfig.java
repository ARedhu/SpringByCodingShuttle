package com.codingShuttle.Ashish.SpringAI.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Remember in the newer version of spring ai we have to define the advisors here only, we can't use builder later.
// But then what if someone later wants to use only two advisors and someone else wants to use 4-advisors. So, it is better to define each advisor seperately and not to club them so that later users can combine them as per their needs.
@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
                .build();
    }

    @Bean
    public VectorStoreChatMemoryAdvisor vectorStoreChatMemoryAdvisor(VectorStore vectorStore){
        return VectorStoreChatMemoryAdvisor.builder(vectorStore)
                .build();
    }

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor(){
        return new SimpleLoggerAdvisor();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository){
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(20)
                .build();
    }
    // What does each part do?
    //1. ChatMemoryRepository chatMemoryRepository: Spring automatically injects the repository bean. Because you've configured JDBC-backed chat memory in pom.xml, this repository stores and retrieves conversation messages from PostgreSQL.
    //2. MessageWindowChatMemory.builder(): Creates a builder to configure your short-term memory implementation.

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory chatMemory){
        return MessageChatMemoryAdvisor.builder(chatMemory)
                .build();
    }
    // Remember MessageChatMemoryAdvisor will accept the instance of only ChatMemory. It manages conversation messages using a configured storage repository.And, it is a short memory which bydefault contains last 20-messages only. We can alter it as well. ChatMemory is an abstraction, not a database or table itself. Its implementation can use in-memory storage or a persistent repository, such as JDBC-backed storage.
    // And, we configure these variables ChatMemory, VectorStore in application.yml file.
}

// ChatClient: It helps us to communicate with an AI model.
// ChatModel works under chatclient.
