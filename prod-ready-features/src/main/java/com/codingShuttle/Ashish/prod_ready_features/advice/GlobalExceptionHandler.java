package com.codingShuttle.Ashish.prod_ready_features.advice;

import com.codingShuttle.Ashish.prod_ready_features.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// We can't change the name of "advice" folder but we can change the name of "GlobalExceptionHandler" folder but it is a preferred name by developers.
//Just to clarify—you can absolutely change the name of the "advice" folder! It's just a folder package name. You can call it handlers, exceptions, errors, or anything you like. As long as the class has the @RestControllerAdvice annotation, Spring will find it anywhere in your component-scan range!
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // Here we are telling that the below function/exceptionhandler will work for this mentioned exception inside of brackets.
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}

/*
       Question: What is Response Entity?
       Ans:
        In Spring Boot, ResponseEntity is a special class that represents the entire HTTP response being sent back to the client (like Postman or a frontend app).

        Think of it as a wrapper box. Inside standard methods, returning a plain object like PostDto or ApiError only fills the Body of the response, and Spring forces a default status code (200 OK).

        By using ResponseEntity, you gain total programmatic control over all three parts of an HTTP response:
        The Status Code: (e.g., 200 OK, 404 NOT_FOUND, 201 CREATED)
        The Headers: (e.g., custom metadata, content types)
        The Body: The actual data payload (ApiError, PostDto, etc.)

 */