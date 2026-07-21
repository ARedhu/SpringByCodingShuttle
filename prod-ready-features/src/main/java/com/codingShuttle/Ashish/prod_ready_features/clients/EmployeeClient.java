package com.codingShuttle.Ashish.prod_ready_features.clients;


import com.codingShuttle.Ashish.prod_ready_features.dto.EmployeeDTO;

import java.util.List;

// In production applications, we usually create a dedicated package for external API communication. And, the name of the package is "client"
public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long employeeId);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
}
