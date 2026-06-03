package com.codingshuttle.module1introduction.repositories;

import com.codingshuttle.module1introduction.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // it's beans would be created now.
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}

