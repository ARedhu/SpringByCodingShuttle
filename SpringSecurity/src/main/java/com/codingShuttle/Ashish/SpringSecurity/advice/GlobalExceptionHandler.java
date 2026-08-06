package com.codingShuttle.Ashish.SpringSecurity.advice;

import com.codingShuttle.Ashish.SpringSecurity.advice.ApiError;
import com.codingShuttle.Ashish.SpringSecurity.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

// We can't change the name of "advice" folder but we can change the name of "GlobalExceptionHandler" folder but it is a preferred name by developers.
//Just to clarify—you can absolutely change the name of the "advice" folder! It's just a folder package name. You can call it handlers, exceptions, errors, or anything you like. As long as the class has the @RestControllerAdvice annotation, Spring will find it anywhere in your component-scan range!
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // Here we are telling that the below function/exceptionhandler will work for this mentioned exception inside of brackets.
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


//    ---------- Spring Security Exception Handling -----------
    // Bydefault spring security gives us 403 error which means the user is authenticated but not authorized which is not correct for some cases.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
   // Various other exceptions comes under AuthenticationException:
    // i) AccountExpiredException, ii) BadCredentialsException, iii) CredentialsExpiredException, iv) AuthenticationCredentialsNotFoundException, v) SessionAuthenticationException.

    // Various other exceptions comes under JwtException:
    // i) ExpiredJwtException, ii) MalformedJwtException, iii) SignatureException, iv) UnsupportedJwtException, v) IllegalArgumentException.
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
    // But remember this JwtException will not work directly, just by writing the upper method.
    // Reason:
    // Because of various contexts. Like we have application-context for the whole application. One is dispatcherServletContext, this context is controllers and services. If any error occur with dispatcherServeletContext then that only will be handled by GlobalExceptionHandler. But our JwtError may occur inside of "filters/JwtAuthFilter" which will not be handled bydefault by GlobalExceptionHandler. Then, how we can pass our exception of "filters" package to this DispatcherServeletException context. For that we need "ExceptionResolver"
}

