package com.codingShuttle.Ashish.SpringAI.service;

import com.codingShuttle.Ashish.SpringAI.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    // Way-1:
//    public String getJoke(String topic){
//        return chatClient.prompt() // Starts building a prompt for the AI.
//                .system("You are a sarcastic joker, give jokes of 3 lines")
//                .user("Give me a joke on the topic: "+topic) // Sets the user's message
//                .call() // Sends the prompt to the AI model.
//                .content(); // Extracts the AI's response as a String
//    }

    // Way-2:
    public String getJoke(String topic){
        // To give large prompts
        String promt1 = """
            You are a sarcastic joker, give response in 4-lines.
            You don't make joke about politics.
            Give a joke on the topic: {topic}
            """; // Whatever we provide inside of curly braces will become a variable. And, we prefer this way because there could be multiple variables and it is a better way.

        PromptTemplate promptTemplate = new PromptTemplate(promt1);
        String promt2 = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(promt2)
                .advisors( // Advisors stands b/w req/res. These helps us to modify or perform certain actions before the promt is send to AI and after the response comes back from AI, but before serving to the backend server of our application.
                        new SimpleLoggerAdvisor()
                )
                .call()
//                .content();
                .entity(Joke.class); // here it will automatically convert the JSON response to Java object. Rememeber it will modify the promt as well. Because it will also say hey return only a JSON format so that later spring ai can convert it to java object.

        System.out.println(response);

        return response.text();
    }

    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);
    }

    public void ingestDataToVectorStore(){
        // Note: We have to convert the data to document and then we can store it in the vector store. Because then only it will store embedding.
        List<Document> movies = List.of(
                new Document("A thief who steals corporate secrets through the use of dream-sharing technology",  // This text will be converted into an embedding.
                        Map.of("title", "Inception", "genre", "Sci-Fi", "year", "2019")), // This is the meta-data stored in the form of key-value pairs.
                new Document("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival",
                        Map.of("title", "Interstellar", "genre", "Sci-Fi", "year", "2014")),
                new Document("A poor yet passionate young man falls in love with a rich young woman",
                        Map.of("title", "The Notebook",  "genre", "Romance", "year", "2006"))
        );
        vectorStore.add(movies);
    }

    public List<Document> similaritySearch(String text){
//        return vectorStore.similaritySearch(text);

        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(2)
                .build());
    }

    public String askAI(String promt){
        String template = """
                Rules:
                - Use only the information provided in the context
                - If multiple context sections are relevant, combine them into a single explanation.
                - If the answer is not present, say "I don't know"
                
                Context:
                {context}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(promt)
                        .topK(2)
//                        .filterExpression(" topic=='ai or topic=='vectorstore' ")
                        .filterExpression(" file_name=='resume.pdf' ")
                        .build());

        String context = documents.stream()
                .map(doc -> doc.getText())
                .collect(Collectors.joining("\n\n"));

        String systemPromt = promptTemplate.render(Map.of("context", context));

        return chatClient.prompt()
                .system(systemPromt)
                .user(promt)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .content();


    }

}
