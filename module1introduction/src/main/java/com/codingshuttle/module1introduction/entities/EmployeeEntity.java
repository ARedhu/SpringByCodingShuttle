package com.codingshuttle.module1introduction.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity // Marks the class as a JPA Entity. Tells Hibernate: “Create/manage a database table for this class.”
@Getter
@Setter // If we have Lombok library.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee") // Else the table name will be same as the class name.
public class EmployeeEntity {

    @Id // Marks the primary key of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Remember we still have to use this id variable here becasue @Id will not create a variable by itself it is just an annotation.

    private String name;

    @Column(name = "student_name") // Used for custom column configuration.
    private String emailId;

    private int age;



    // If we have Lombok library then we don't have to define constructors, getters and setters by ourself.

    // Constructors
//    public Employee() {
//
//    }

//    public Employee(Long id, String name, String email) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//    }
    // Getters and Setters
}