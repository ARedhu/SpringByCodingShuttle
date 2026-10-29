package com.codingShuttle.Ashish.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RAGService {

    @Value("classpath:resume.pdf")
    Resource pdfResume;

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    private final MessageChatMemoryAdvisor messageChatMemoryAdvisor;
    private final VectorStoreChatMemoryAdvisor vectorStoreChatMemoryAdvisor;
    private final SimpleLoggerAdvisor simpleLoggerAdvisor;

    // This all will happen offline, as we are storing things in our database. And, will happen once.
    public void ingestPdf(){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResume);
        List<Document> pages = reader.get();

        TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .build();

        List<Document> chunks = textSplitter.apply(pages);
        vectorStore.add(chunks);
    }

    public String askAIWithAdvisors(String prompt, String userId){

        List<Advisor> advisors = new ArrayList<>();
        advisors.add(simpleLoggerAdvisor);
        advisors.add(messageChatMemoryAdvisor);
        advisors.add(vectorStoreChatMemoryAdvisor);

        // We have to provide conversationId because that helps to differentiate b/w multiple users. We can get that from spring security.

        return chatClient.prompt()
                .user(prompt)
                .advisors(spec -> spec
                        .advisors(advisors)
                        .param(ChatMemory.CONVERSATION_ID, userId)
                )
                .call()
                .content();
    }
}
