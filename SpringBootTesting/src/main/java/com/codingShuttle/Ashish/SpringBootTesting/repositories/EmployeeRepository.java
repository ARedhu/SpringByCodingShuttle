package com.codingShuttle.Ashish.SpringBootTesting.repositories;

import com.codingShuttle.Ashish.SpringBootTesting.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}

