package com.codingshuttle.module1introduction.controllers;

import com.codingshuttle.module1introduction.dto.EmployeeDTO;
import com.codingshuttle.module1introduction.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee") // if we have same URL/path starting for all controllers inside then we can take it outside for simplicity.
public class EmployeeControllers {

    final private EmployeeService employeeService;

    public EmployeeControllers(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    // 1. @PathVariable mapping
    // Use path variable when the parameter is the essential part of the URL path that identifies a resource.
//     @GetMapping(path="/employee/{employeeId}")
//    @GetMapping(path="/{employeeId}")
//    public EmployeeDTO getEmployeeById(@PathVariable (name="employeeId") Long id){
//       return new EmployeeDTO(id, "Ashish", "ashish@gmail.com", 23);
       // See here we are not converting the Data from Java object to JSON but client it receiving it in the form of JSON object because of Jackson which converts Java Object to JSON and vice-versa if we use @RestController.

         // Using service.
//         return employeeService.getEmployeeById(id);
//     }

    // *** It is preferred to return ResponseEntity rather than DTO because we can send various other things as well along with the response object like status codes.
    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("employeeId") Long id){
        Optional<EmployeeDTO> employeeDTOOptional =  employeeService.getEmployeeById(id);
        // It is not necessary that map method can be used only on lists. Diff classes provide their own map() method.
        // map with optional means transforms the value if it is present.
        ResponseEntity<EmployeeDTO> responseEntity = employeeDTOOptional
                                            .map(employeeDTO -> ResponseEntity.ok(employeeDTO))
                                            .orElse(ResponseEntity.notFound().build());
        return responseEntity;
    }

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

    @GetMapping(path="")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @PostMapping(path="")
    public ResponseEntity<EmployeeDTO> postEmployeeBy(@RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO savedEmployee = employeeService.postEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping(path="/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId){
            EmployeeDTO updatedEmployee = employeeService.putEmployee(employeeId, employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeId){
        boolean isDeleted = employeeService.deleteEmployeeById(employeeId);
        if(!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeePartially(@PathVariable("employeeId") Long id, @RequestBody Map<String, Object> updates){
        Optional<EmployeeDTO> employeeDTO = employeeService.patchEmployee(id, updates);
        return employeeDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
