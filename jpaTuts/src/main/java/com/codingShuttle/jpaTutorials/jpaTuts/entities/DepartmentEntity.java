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
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Note:
    // There could be multiple kind of mappings also, b/w just two tables only. This is based on the column.

    @OneToOne
    @JoinColumn(nullable = false)
    private DoctorEntity headDoctor;

    @ManyToMany
    private List<DoctorEntity> doctorEntityList = new ArrayList<>();


    // We read it something like: Many-departments-belongsTo-Many-Doctors.
    // OneToMany + ManyToOne = ManyToMany;

}
