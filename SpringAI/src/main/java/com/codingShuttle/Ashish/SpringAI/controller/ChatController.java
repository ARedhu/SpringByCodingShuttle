package com.codingShuttle.Ashish.SpringAI.controller;

import com.codingShuttle.Ashish.SpringAI.tool.FlightBookingTools;
import com.codingShuttle.Ashish.SpringAI.tool.TravellingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private  final ChatClient chatClient;
    private  final TravellingTools travellingTools;
    private final FlightBookingTools flightBookingTools;
    private final MessageChatMemoryAdvisor messageChatMemoryAdvisor;

    @PostMapping("/chat")
    public String chat(@RequestBody String message, @RequestParam String userId){

        // Remember when we have spring security implemented then we can get the userid directly from spring security context holder.
        String systemPrompt = String.format("""
                IMPORTANT: The current user's ID is "%s". 
                When calling tools that require a userId, ALWAYS use this exact value.
                """, userId);

        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .tools(travellingTools, flightBookingTools)
                .advisors(spec -> spec
                        .advisors(messageChatMemoryAdvisor)
                        .param(ChatMemory.CONVERSATION_ID, userId)
                )
                .call()
                .content();
    }
}
