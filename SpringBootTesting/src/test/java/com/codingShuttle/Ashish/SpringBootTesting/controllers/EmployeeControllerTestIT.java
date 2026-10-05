package com.codingShuttle.Ashish.SpringBootTesting.controllers;

import com.codingShuttle.Ashish.SpringBootTesting.dto.EmployeeDto;
import com.codingShuttle.Ashish.SpringBootTesting.entities.Employee;
import com.codingShuttle.Ashish.SpringBootTesting.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository; // Remember we will use all the things real like service, repository. Just the database will be dummy/test database.

    Employee testEmployee;
    EmployeeDto testEmployeeDto;

    @BeforeEach
    void setUp() {
        testEmployee = Employee.builder()
                .email("Ashish@gmail.com")
                .name("Ashish")
                .salary(200L)
                .build();
        testEmployeeDto = EmployeeDto.builder()
                .email("Ashish@gmail.com")
                .name("Ashish")
                .salary(200L)
                .build();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success(){
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get() // Saying that you have to make a get request
                .uri("/employees/{id}", savedEmployee.getId()) // the request.
                .exchange() // make the request and take the response. Before it the request lines are there and after it the response lines are there.
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class) // Converting the incoming response from server to EmployeeDto type. Means we are deserealizing the data. Jackson is working here to convert raw JSON data to our object type of employeedto.
               // .isEqualTo(testEmployeeDto)  // Way-1 of comparison: Here we are directly comparing it with testEmployeeDto. Use it when our .equals and .hashcode methods inside of EmployeeDto class are properly written according to our needs
                .value(employeeDto -> { // way-2:
                    assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
                    assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
                });
    }


    @Test
    void testGetEmployeeById_failure(){
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail()) // way-3: directly check with the json.
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

}