package com.codingShuttle.Ashish.SpringAI.service;

import com.codingShuttle.Ashish.SpringAI.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

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

}
