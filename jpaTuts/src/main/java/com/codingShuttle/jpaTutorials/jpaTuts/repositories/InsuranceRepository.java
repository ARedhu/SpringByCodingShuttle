package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.InsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Long> {
}
