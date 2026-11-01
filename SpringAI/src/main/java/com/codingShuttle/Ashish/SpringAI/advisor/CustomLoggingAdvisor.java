package com.codingShuttle.Ashish.SpringAI.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

public class CustomLoggingAdvisor implements CallAdvisor {

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {

        // Before LLM execution
        System.out.println("Before LLM: " + request.prompt().getUserMessage().getText());

        // Continue the advisor chain
        ChatClientResponse response = chain.nextCall(request);

        // After LLM execution
        System.out.println("After LLM: " + response.chatResponse().getResult().getOutput().getText());

        return response;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
