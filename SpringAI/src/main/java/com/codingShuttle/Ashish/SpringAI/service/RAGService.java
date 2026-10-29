package com.codingShuttle.Ashish.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAGService {

    @Value("classpath:resume.pdf")
    Resource pdfResume;

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

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

    public String askAIWithAdvisors(String prompt){

        return chatClient.prompt()
                .user(prompt)
                .advisors(
                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .conversationId(userId)
                                .build()
                )
                .call()
                .content();
    }
}
