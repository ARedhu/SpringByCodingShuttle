package com.codingShuttle.jpaTutorials.jpaTuts.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "appointment_patient")
    private PatientEntity patientEntity;

    @ManyToOne
    @JoinColumn(nullable = false, name = "appointment_doctor")
    private DoctorEntity doctorEntity;

}

/*
@OneToOne(fetch = FetchType.LAZY); // In case of OneToOne and ManyToOne, the fetch type is EAGER bydefault. Means it will try to fetch the associted child entity fields automatically.
// We should make it lazy so that the loading and initial req/res is fast of our website. If it is requested later then we can fetch the child entity field also.

But in case of @OneToMany, it is bydefault LAZY.

 */