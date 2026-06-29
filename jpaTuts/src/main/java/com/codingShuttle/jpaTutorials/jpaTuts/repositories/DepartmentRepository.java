package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
