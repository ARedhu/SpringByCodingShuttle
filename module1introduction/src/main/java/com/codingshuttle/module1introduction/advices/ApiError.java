package com.codingshuttle.module1introduction.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private String message;
    private HttpStatus status;
    List<String> subErrors;
}


//@Data is an annotation provided by Project Lombok that automatically generates boilerplate code for a Java class.
//Instead of manually writing:
//    getters
//    setter
//    toString()
//    equals()
//    hashCode()
//    constructor


// @Builder: It helps create objects in a clean, readable, and flexible way — especially when a class has many fields.
//        | Advantage                   | Explanation                        |
//        | --------------------------- | ---------------------------------- |
//        | Readability                 | Easy to understand object creation |
//        | Flexible                    | Set only required fields           |
//        | Avoid Constructor Confusion | No parameter order issue           |
//        | Cleaner Code                | Especially for many fields         |
//        | Immutable Objects           | Often used with immutable classes  |


// Without builder object creation:
// Employee emp = new Employee(
//        1L,
//        "Ashish",
//        "ashish@gmail.com",
//        23
// );

// With builder object creation:
//EmployeeDTO dto = EmployeeDTO.builder()
//        .id(101L)
//        .name("Rahul")
//        .email("rahul@gmail.com")
//        .build();

// Remember buider will not help us to create objects using beans. It is just a simple way of creating normal objects.
