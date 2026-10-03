package com.codingShuttle.Ashish.SpringBootTesting.services.impl;

import com.codingShuttle.Ashish.SpringBootTesting.TestContainerConfiguration;
import com.codingShuttle.Ashish.SpringBootTesting.dto.EmployeeDto;
import com.codingShuttle.Ashish.SpringBootTesting.entities.Employee;
import com.codingShuttle.Ashish.SpringBootTesting.exceptions.ResourceNotFoundException;
import com.codingShuttle.Ashish.SpringBootTesting.repositories.EmployeeRepository;
import com.codingShuttle.Ashish.SpringBootTesting.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


//@Import(TestContainerConfiguration.class) // Use this database.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Don't use any other database available.
@ExtendWith(MockitoExtension.class) //1.i Here we can't use @DataJpaTest as that will not scan and create beans for Service layer.
class EmployeeServiceImplTest {

    @Mock // 2.i
    private EmployeeRepository employeeRepository;

    @Spy // 3.i Use it when we don't want to create a mock or not even the bean of particular thing. And, still want to use its actual version inside of tests.
    private ModelMapper modelMapper;

    @InjectMocks //1.ii Creates the service object and injects the @Mock dependencies into it.
    private EmployeeServiceImpl employeeService; // Also as we are not creating a bean directly so we can't use EmployeeService. We have to use EmployeeServiceImpl.

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp(){
        Long id = 1L;
        employee = Employee.builder()
                .id(id)
                .name("Ashish")
                .email("Ashish@gmail.com")
                .salary(200L)
                .build();

        employeeDto = modelMapper.map(employee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto(){ // +ve case.
//        Assign
        Long id = employee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee)); // 2.ii stubbing.

//        Act
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

//        Assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getEmail()).isEqualTo(employee.getEmail());
        verify(employeeRepository).findById(id); // 2.iii) Verify if the mocked thing methods were properly called or not.
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIsNotPresent_ThenThrowException(){ // -ve case.
        // Arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(()-> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

    }


    @Test
    void testCreateNewEmmployee_WhenValidEmployee_ThenCreateNewEmployee(){ // Happy or +ve case. Means we are not dealing with failure or exceptions in this test case.
//        Assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of()); // Stubbing.
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee); // stubbing.

//        Act
        EmployeeDto newEmployeeDto = employeeService.createNewEmployee(employeeDto);

//        Assert

        // 4.i) Argument captor helps us to capture the value that we passed inside the method of a mocked thing. This is useful because it may happen that our service modifies the object before saving. So, we want to verify exactly what was sent to the repository.
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(newEmployeeDto.getEmail());

        //        verify(employeeRepository).save(any(Employee.class)); // Before capture.
        // Ye verify method to simply verify karta h ki save method call hua tha ki nahi.
        verify(employeeRepository).save(employeeArgumentCaptor.capture()); // This tells to Mockito that When you find the Employee that was passed to save(), capture that actual object inside captor.

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(employee.getEmail()); // Here we are verifying even more tightly that what we are passing from service layer is actually equal to what is passed to the repo for saving.
    }


    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException(){
        // Assert
        String email = employee.getEmail();
        when(employeeRepository.findByEmail(email)).thenReturn(List.of(employee));

        // act and asssert
        assertThatThrownBy(()-> employeeService.createNewEmployee(employeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: "+email);

        verify(employeeRepository).findByEmail(employeeDto.getEmail());
        verify(employeeRepository, never()).save(any());
    }


}