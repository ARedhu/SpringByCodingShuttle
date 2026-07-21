package com.codingShuttle.Ashish.prod_ready_features.clients.impl;

import com.codingShuttle.Ashish.prod_ready_features.advice.ApiResponse;
import com.codingShuttle.Ashish.prod_ready_features.clients.EmployeeClient;
import com.codingShuttle.Ashish.prod_ready_features.dto.EmployeeDTO;
import com.codingShuttle.Ashish.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class); // Here it is necessary to specify the name of the class for which we are creating this logger. So, that the logger can log the name of the class properly while logging in the file/console.

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        // Remember the below logs have no effect, these are just the marker for the understanding of logs by the fellow programmers.
        // log.error("error log");
        // log.warn("warn log");
        // log.info("info log");
        // the below two logs will not get printed as bydefault only till "INFO" logs are enabled for root. We can change it in "application.properties" file.
        // log.debug("debug log");
        // log.trace("tracel log");

        // Write only trace here. Don't write "INFO"
        log.trace("Trying to retrieve all employees in getAllEmployees");
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                                                        .uri("employee")// This thing append in the base url.
                                                        .retrieve()
                                                        .body(new ParameterizedTypeReference<>(){}); // It tells the type of response we want. If we have a single employee then we could have easily written like "EmployeeDTO.class". But as we have a parameterized list here. So, we used this.
            // There can occur various errors in the upper mentioned code lines like client, server, connection errors.
            log.debug("Successfully retrived the employees in getAllEmployees");
            log.trace("Retrieved employees list in getAllEmployees: {}, {}", employeeDTOList.getData(), "done"); // It is better to use "{}" as compared to string concatination as we can directly pass multiple variables data in a more organized way.
            return employeeDTOList.getData();
        }
        catch(Exception e){
            log.error("Exception occured in getAllEmployes: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("Trying to get Employee by id in getEmployeeById with id: {}", employeeId);
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
                        log.debug("4xxClient error occurred during createNewEmployee");
                        log.error(new String(res.getBody().readAllBytes()));
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
