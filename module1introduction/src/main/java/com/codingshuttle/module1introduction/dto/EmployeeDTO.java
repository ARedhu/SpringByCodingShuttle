package com.codingshuttle.module1introduction.dto;

import com.codingshuttle.module1introduction.annotations.EmployeeValidation;
import jakarta.validation.constraints.*;

public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "name can't be blank")
    @Size(min = 11, max=21, message = "size of name should be in the range: [3,13] ")
    @EmployeeValidation
    private String name;

    @Email(message = "Enter a valid email")
    private String emailId;

    @Max(value = 80, message = "age can't be greater than 80")
    @Min(value = 18, message = "age can't be less than 18")
    private int age;

    public EmployeeDTO(){}

    public EmployeeDTO(Long id, String name, String emailId, int age) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
