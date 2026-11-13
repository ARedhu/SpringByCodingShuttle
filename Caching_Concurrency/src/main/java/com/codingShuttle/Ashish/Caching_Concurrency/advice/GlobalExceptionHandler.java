package com.codingShuttle.Ashish.Caching_Concurrency.advice;

import com.codingShuttle.Ashish.Caching_Concurrency.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.notFound().build();
    }

    // The below exception is mainly related to concurrency and optimistic locking in Hibernate/JPA.
    // It means The entity you're trying to update is no longer in the state that Hibernate expected it to be.
    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<?> handleStaleObjectState(StaleObjectStateException ex) {
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>("Stale data\n", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
