package com.codingshuttle.module1introduction.services;

import com.codingshuttle.module1introduction.dto.EmployeeDTO;
import com.codingshuttle.module1introduction.entities.EmployeeEntity;
import com.codingshuttle.module1introduction.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

// Dekho service ka kaam DTO <-> entity me convert karna aur conditions likhna h, repository database se chijein la ke dega aur controller bas req/res handle karega.
@Service
public class EmployeeService {

    final private EmployeeRepository employeeRepository;
    final private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .toList();
    }

    public EmployeeDTO postEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }


}
