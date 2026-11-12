package com.codingShuttle.Ashish.Caching_Concurrency.repository;


import com.codingShuttle.Ashish.Caching_Concurrency.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}
