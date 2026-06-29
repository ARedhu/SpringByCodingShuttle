package com.codingShuttle.jpaTutorials.jpaTuts.repositories;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
