package com.codingShuttle.jpaTutorials.jpaTuts.entities;

import com.codingShuttle.jpaTutorials.jpaTuts.enums.BloodGroup;
import com.codingShuttle.jpaTutorials.jpaTuts.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING) //Recommended: stores the enum name as text in the database.
    @Column(nullable = false)
    private Gender gender;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Patient-Insurance relationship.
    // This is the new thing to Learn.
    // Owner side. for single source of truth.
//    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "patient_insurance") // Now the database tab
    // le would have a join column with the name "patiend_insurance". Else the name of the column in the DB would be the name of variable in the entity here "insuranceEntity_id", the id name of the insurance.
    private InsuranceEntity insuranceEntity;

    // Appointment-Patient relationship
    @OneToMany(mappedBy = "patientEntity", cascade = CascadeType.ALL) // Inverse side in this relationship.
    private List<AppointmentEntity> appointmentEntityList = new ArrayList<>();
    // Q. Why cascading on Inverse side.
    // Ans. Eventhough this PatientEntity is the inverse side for AppointmentEntity. But still we can understand that if the patient is deleted then appointment(entities related to it should also be deleted). That's why we set the Cascading to inverse Entity. Becasue it is parent here in this relationship.
}


// Cascading: Cascading is a JPA feature that automatically propagates operations performed on a parent entity to its associated child entities.
/*
public enum CascadeType {

    ALL,        // Applies all cascade operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH).

    PERSIST,    // When the parent is saved, the child is also saved.

    MERGE,      // When the parent is updated (merged), the child is also updated.

    REMOVE,     // When the parent is deleted, the child is also deleted.

    REFRESH,    // When the parent is refreshed from the database, the child is also refreshed.

    DETACH;     // When the parent is detached from the Persistence Context, the child is also detached.
}
*/

/*
CascadeType.REMOVE deletes child entities only when the parent is deleted.
orphanRemoval = true deletes child entities when they are no longer referenced by the parent even if the parent remains in the database.
 */