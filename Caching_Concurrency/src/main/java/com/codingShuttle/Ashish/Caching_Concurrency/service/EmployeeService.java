package com.codingShuttle.Ashish.Caching_Concurrency.service;

import com.codingShuttle.Ashish.Caching_Concurrency.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
