package com.codingShuttle.jpaTutorials.jpaTuts;

import com.codingShuttle.jpaTutorials.jpaTuts.entities.AppointmentEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.entities.InsuranceEntity;
import com.codingShuttle.jpaTutorials.jpaTuts.services.AppointmentService;
import com.codingShuttle.jpaTutorials.jpaTuts.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testAssignInsuranceToPatient(){
        InsuranceEntity newInsurance = InsuranceEntity.builder()
                .policyNumber("1235")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030, 1, 12))
                .build();

        var updatedInsurance = patientService.assignInsuranceToPatient(newInsurance, 1L);
        System.out.println("Updated Insurance is: "+updatedInsurance);

    }


    @Test
    public void testCreateAppointment(){
        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .appointmentTime(LocalDateTime.of(2026, 7, 15, 14, 23, 1))
                .reason("Back Pain")
                .build();

        AppointmentEntity newlyCreatedAppointment = appointmentService.createANewAppointment(appointmentEntity, 1L, 3L);
        System.out.println("Newly Created Appointment is: "+newlyCreatedAppointment);

    }

    @Test
    public void testPatientDelete(){
        patientService.deletePatient(1L);
    }
}
