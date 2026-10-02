package com.codingShuttle.Ashish.SpringBootTesting.repositories;

import com.codingShuttle.Ashish.SpringBootTesting.TestContainerConfiguration;
import com.codingShuttle.Ashish.SpringBootTesting.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Arrange → Prepare test data
Act     → Execute the method
Assert  → Verify the result
 */

@Import(TestContainerConfiguration.class)
//@SpringBootTest // Avoid this for repository tests; it loads the entire application context and is slower. It is mainly used for integration testing.
@DataJpaTest // We use this for Persistance layer testing. It will automatically configure the database other than our production database if available. Like here we have h2-database. Also it runs each test case within a transaction, which is rolled back after test completion. This ensures tests do not affect each other and provides a clean state for each other.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // We use it so that after using TestContainer docker image as our testing database, we don't want h2-database to initialize. Or we can skip using it and remove h2-database from dependencies.
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .name("Ashish")
                .email("Ashish@gmail.com")
                .salary(100L)
                .build();
    }

    // Note: The first method that executes inside a file will cache the application context. So, it makes more time to execute. Other methods can simply use that cached context and run very fast.
    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() { // Happy test case : Positive test case.
        //        Arrange: prepared the data.
        employeeRepository.save(employee); // save this data inside of test database.

        //        Act: run the method.
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        //        Assert: verify the results.
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isNotEmpty();
        Assertions.assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_WhenEmailIsNotFound_thenReturnEmptyEmployeeList(){ // Negative test case.
        // Assign
        String email = "hello@gmail.com";

        // Act
        List<Employee> employeeList = employeeRepository.findByEmail(email);

        // Assert
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isEmpty();

    }
}


