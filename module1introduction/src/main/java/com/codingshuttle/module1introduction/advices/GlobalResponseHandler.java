package com.codingshuttle.module1introduction.advices;

import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//Note: This is an advanced feature of Spring Boot used for intercepting and modifying API responses globally before they are sent to the client.
/* I want to intercept every controller response
    before it goes to the client. */

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // ALL controller responses are intercepted
        // return returnType.getContainingClass() == EmployeeController.class; // only EmployeeController responses modified
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // body contains whatever the controller method returned. Then we modify it and return from here.
        if(body instanceof ApiResponse<?>) return body; // We added this extra check because it may happen that some other developer already wrapped the body in ApiResponse in controller. So, to prevent Double wrapping.

        // Either add this condition here or update the GlobalExceptionHandler where we are returning ApiError. We have to change that to object of ApiResponse(ApiError). Else bydefault ApiResponse will treat Error as a normal object and call that object<T> type of constructor.
        if(body instanceof ApiError apiError) {
            return new ApiResponse<>(apiError);
        }

        return new ApiResponse<>(body);
    }
}
//ApiResponse<?> means ApiResponse of any type.

/*
        Controller Response
        ↓
        GlobalResponseHandler
        ↓
        Modified Response
        ↓
        Client
 */

/*
implements ResponseBodyAdvice<Object>
       This tells Spring:
       "This class can modify HTTP response bodies."
*/



