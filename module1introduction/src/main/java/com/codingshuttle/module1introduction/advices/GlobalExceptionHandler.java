package com.codingshuttle.module1introduction.advices;

import com.codingshuttle.module1introduction.exceptions.ResourceNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice // This binds all the rest controllers to this class.
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(ResourceNotFoundException ex){
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    // We can define multiple exceptions here.
    @ExceptionHandler(Exception.class) // This will handle rest all the exceptions except ResourceNotFoundException as that is already handled.
    public ResponseEntity<ApiError> internalServerException(Exception ex){
        ApiError apiError = ApiError
                                .builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message(ex.getMessage())
                                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // MethodArgumentNotValidException occurs when request validation fails for an object annotated with @Valid.
    // MethodArgumentNotValidException is thrown:
        // before service layer executes
        // during request validation phase
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentInValid(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult() // it basically binds all the errors togethers and returns in the form of a single error.
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ApiError apiError = ApiError
                                .builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Input validation failed")
                                .subErrors(errors)
                                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST); // Bad request because user is not passing correct inputs.

    }
}

// Generally we don't only return a string as the response of the exception. But we return a proper object which is having status code, timestamp, message etc.

