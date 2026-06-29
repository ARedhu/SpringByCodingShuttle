package com.codingShuttle.jpaTutorials.jpaTuts.services;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.InsuranceEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.entities.PatientEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.InsuranceRepository;
import com.codingShuttle.jpaTutorials.jpaTuts.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;


    public PatientService(InsuranceRepository insuranceRepository, PatientRepository patientRepository) {
        this.insuranceRepository = insuranceRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    // @Transactional makes the entire method execute within a single database transaction. During the execution of the method, one Persistence Context is associated with the transaction, so all entities loaded or saved are managed by the same Persistence Context. If the method completes successfully, the transaction is committed and all changes are saved to the database. If a runtime exception occurs, the transaction is rolled back, and none of the changes made within that transaction are persisted.
    public InsuranceEntity assignInsuranceToPatient(InsuranceEntity insuranceEntity, Long patientId){
        // insuranceEntity is in Transient state as it has been created using "new" keyword but has not been added to database.

        PatientEntity patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsuranceEntity(insuranceEntity);
        // We did this patient and not to insurance as patient is owning the mapping.
        // Here we dirtied the patient, means made somem changes. Now patient will be synchronized with the DB.
        // But still it will give an error as the patient(persistant State entity) is trying to access the insuranceEntity(Transient state entity). We have to convert insuranceEntity from Transient -> persistent state. We can do this using Cascading.

        // Optional: We do this if we are trying to operate on insuranceEntity below in the same function.
        insuranceEntity.setPatientEntity(patient);
        return insuranceEntity;
    }

    @Transactional
    public void deletePatient(Long patientId){
        patientRepository.findById(patientId).orElseThrow();
        patientRepository.deleteById(patientId);
    }
}
