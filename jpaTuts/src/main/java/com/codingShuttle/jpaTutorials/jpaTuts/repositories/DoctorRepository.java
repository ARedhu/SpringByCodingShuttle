package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
