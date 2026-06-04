package com.codingshuttle.module1introduction.services;

import com.codingshuttle.module1introduction.dto.EmployeeDTO;
import com.codingshuttle.module1introduction.entities.EmployeeEntity;
import com.codingshuttle.module1introduction.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.springframework.data.util.ReflectionUtils.*;

// Dekho service ka kaam DTO <-> entity me convert karna aur conditions likhna h, repository database se chijein la ke dega aur controller bas req/res handle karega.
@Service
public class EmployeeService {

    final private EmployeeRepository employeeRepository;
    final private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    // Way-1:
//    public EmployeeDTO getEmployeeById(Long id) {
//        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
//        return modelMapper.map(employeeEntity, EmployeeDTO.class);
//    }
    // Way-2:
//    In modern Spring Boot applications, returning Optional<EmployeeDTO> from the service layer is generally considered better than returning null.
    public Optional<EmployeeDTO> getEmployeeById(Long id){
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        // It is not necessary that map method can be used only on lists. Diff classes provide their own map() method.
        // map with optional means transforms the value if it is present.
        Optional<EmployeeDTO> employeeDTO = employeeEntity.map(empEntity ->
            modelMapper.map(empEntity, EmployeeDTO.class)
        );
        return employeeDTO;
    }

    // We don't have to use optional here because if no-employee is present then we will get the empty list, not null.
    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()// if list is empty stream will not run, map will not run, just tolist will return an empty list.
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .toList();
    }

    // Remember we don't have to use optional at every place. Optional is used when we can get null value. Failure in post is treated as an Exception, not as absence of value.
    public EmployeeDTO postEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    // If id exists JPA performs updation, else JPA performs insertion. But remember in DTO the client has to send the whole object (he can't miss some fields except id).
    public EmployeeDTO putEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    // Reflection helps us to dynamically update data. It is useful where there are large number of fields to be updated.
//    public EmployeeDTO patchEmployee(Long id, Map<String, Object> updates) {
//        boolean isExist = employeeRepository.existsById(id);
//        if(!isExist) return null;
//        EmployeeEntity employeeEntity = employeeRepository.findById(id).get(); // 1. Get the entity to be updated.
//        updates.forEach((key, value)->{ // loop over the list.
//            Field fieldtobeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key); // using the key take out the field from the class that we have to update. As the key itself is just a part of a normal list.
//            fieldtobeUpdated.setAccessible(true);
//            ReflectionUtils.setField(fieldtobeUpdated, employeeEntity, value);
//        });
//         We can do the same task using getters/setters as well but that is a very slow and tidious task.
//    return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);


        // Way-2 of patch using optional
        public Optional<EmployeeDTO> patchEmployee(Long id, Map<String, Object> updates){
            Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(id);
            if(employeeOptional.isEmpty()) return Optional.empty();

            EmployeeEntity employeeEntity = employeeOptional.get();
            updates.forEach((key, value)->{
                Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key);
                fieldToBeUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
            });
            EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

            return Optional.of(
                    modelMapper.map(savedEmployee, EmployeeDTO.class)
            );

        }
}
