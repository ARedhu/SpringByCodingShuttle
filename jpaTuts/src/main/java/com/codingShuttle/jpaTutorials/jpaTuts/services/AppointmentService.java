package com.codingShuttle.jpaTutorials.jpaTuts.services;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.AppointmentEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.entities.DoctorEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.entities.PatientEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.AppointmentRepository;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.DoctorRepository;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Then we don't have to write the constructor by ourself.
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public AppointmentEntity createANewAppointment(AppointmentEntity appointmentEntity, Long patientId, Long doctorId){
        PatientEntity patientEntity = patientRepository.findById(patientId).orElseThrow();
        DoctorEntity doctorEntity = doctorRepository.findById(doctorId).orElseThrow();

        appointmentEntity.setPatientEntity(patientEntity);
        appointmentEntity.setDoctorEntity(doctorEntity);

        // But remember this appointment which is the parent class/entity, which is owning the relationship is still in Transient state.
        // So, we will save it to move it and it's children to Persistent state.
        appointmentRepository.save(appointmentEntity);

        return appointmentEntity;
    }
}
