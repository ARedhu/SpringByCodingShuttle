package com.codingShuttle.Ashish.prod_ready_features.dto;

import lombok.ToString;

@ToString
public class EmployeeDTO {

    private Long id;

    private String name;

    private String emailId;

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
