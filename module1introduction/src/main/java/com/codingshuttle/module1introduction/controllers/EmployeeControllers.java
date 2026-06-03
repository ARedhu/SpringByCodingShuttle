package com.codingshuttle.module1introduction.controllers;

import com.codingshuttle.module1introduction.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee") // if we have same URL/path starting for all controllers inside then we can take it outside for simplicity.
public class EmployeeControllers {

    // 1. @PathVariable mapping
    // Use path variable when the parameter is the essential part of the URL path that identifies a resource.
//    @GetMapping(path="/employee/{employeeId}")
//    public EmployeeDTO getEmployeeById(@PathVariable (name="employeeId") Long id){
//        return new EmployeeDTO(id, "Ashish", "ashish@gmail.com", 23);
          // See here we are not converting the Data from Java object to JSON but client it receiving it in the form of JSON object because of Jackson which converts Java Object to JSON and vice-versa if we use @RestController.
//    }

    // 2. @RequestParams
    // Use it when parameter is optional and used for sorting, filtering and other modifications to the request.
    // Browser or Postman has to pass the request params like: /employee?id=15&age=23   "&" is used to combine multiple request params.
//    @GetMapping(path="/employee")
//    @GetMapping(path="") // as we have
//    public EmployeeDTO getEmployeeById(@RequestParam Long id,
//                                       @RequestParam(required = false) int age){
//        return new EmployeeDTO(id, "Ashish", "ashish@gmail.com", age);
//    }

    // 3. @RequestBody
    // 4. @PostMapping
    @PostMapping(path="")
    public EmployeeDTO postEmployeeBy(@RequestBody EmployeeDTO employeeDTO){
        employeeDTO.setId(1L);
        return employeeDTO;
    }
}
