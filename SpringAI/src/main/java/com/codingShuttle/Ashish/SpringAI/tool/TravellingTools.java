package com.codingShuttle.Ashish.SpringAI.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTools {

    // Remember it is not necessary to provide the description and in case of params it is not even necessary to use @ToolParam. Eventhough it is necessary to use @Tool. But provide description is preferred as it tells AI to use which tool and to pass which parameters.
    @Tool(description = "Get the weather of a city")
    public String getWeather(@ToolParam(description = "City name for which to get the weather information") String city){

        // Here we can perform other logic and make api calls.
        switch (city){
            case "Delhi":
                return "Sunny, 26 Degrees";
            case "London":
                return "Cloudy, 10 Degrees";
            default:
                return "City not found";
        }
    }
}
