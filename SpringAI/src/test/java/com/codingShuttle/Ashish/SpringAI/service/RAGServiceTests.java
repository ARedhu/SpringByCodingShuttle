package com.codingShuttle.Ashish.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTests {

    @Autowired
    private RAGService ragService;

    @Test
    public void testIngestPdf(){
        ragService.ingestPdf();
    }

    @Test
    public void testAskAIWithAdvisors(){
        String res = ragService.askAIWithAdvisors("What is my name", "Ashish123");
        System.out.println(res);
    }
}
