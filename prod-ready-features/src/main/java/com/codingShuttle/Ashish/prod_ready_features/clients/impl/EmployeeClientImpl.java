package com.codingShuttle.Ashish.prod_ready_features.clients.impl;

import com.codingShuttle.Ashish.prod_ready_features.advice.ApiResponse;
import com.codingShuttle.Ashish.prod_ready_features.clients.EmployeeClient;
import com.codingShuttle.Ashish.prod_ready_features.dto.EmployeeDTO;
import com.codingShuttle.Ashish.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                                                        .uri("employee")// This thing append in the base url.
                                                        .retrieve()
                                                        .body(new ParameterizedTypeReference<>(){}); // It tells the type of response we want. If we have a single employee then we could have easily written like "EmployeeDTO.class". But as we have a parameterized list here. So, we used this.
            // There can occur various errors in the upper mentioned code lines like client, server, connection errors.
            return employeeDTOList.getData();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try{
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.get()
                                                                    .uri("employee/{employeeId}", employeeId) //  We can pass multiple other variables as well like: .uri("employee/{employeeId}/{abc}", employeeId, "hello")
                                                                    .retrieve()
                                                                    .body(new ParameterizedTypeReference<>(){});
            return employeeDTOApiResponse.getData();
        }
        catch(Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try{
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("employee")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res)->{ // In this way we can handle the exceptions.
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .body(new ParameterizedTypeReference<>(){});
            //      .toEntity(new ParameterizedTypeReference<>(){}); We can get the entity as well, and .header and many more things.


            return employeeDTOApiResponse.getData();
        }
        catch(Exception e){
            throw  new RuntimeException(e);
        }
    }
}
