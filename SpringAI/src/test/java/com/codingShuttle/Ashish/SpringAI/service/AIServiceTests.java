package com.codingShuttle.Ashish.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;

    @Test
    public void testGetJoke(){
        var joke = aiService.getJoke("dogs");
        System.out.println(joke);
    }

    @Test
    public void testGetEmbedding(){
        var embed = aiService.getEmbedding("This is a big text here");
        System.out.println(embed.length);
        for(float a : embed){
            System.out.print(a + " ");
        }
    }

    @Test
    public void testIngestDataToVectorStore(){
        aiService.ingestDataToVectorStore();
    }

    @Test
    public void testSimilaritySearch(){
        var res = aiService.similaritySearch("Space movie");
        System.out.println(res.toArray().length);
        for(var x : res){
            System.out.println(x);
        }
    }

    @Test
    public void testAskAI(){
        var res = aiService.askAI("Who is Ashish");
        System.out.println(res);
    }
}
