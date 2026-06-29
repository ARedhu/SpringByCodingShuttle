package com.codingShuttle.jpaTutorials.jpaTuts.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Remember it is important to define mappedBy here. Here, a new column would not be created in the DB for this inverse entity. This is just for the fast access. If we don't define the mappedBy here, then it will voilate the single source of truth and a new column would be created in the inverse table as well.
    @OneToMany(mappedBy = "doctorEntity")
    private List<AppointmentEntity> appointmentEntityList = new ArrayList<>();
}
