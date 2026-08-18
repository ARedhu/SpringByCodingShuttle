package com.codingShuttle.Ashish.SpringSecurity.dto;

import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Permission;
import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String name;
    private String email;
    private String password;
    private Set<Role> roles; // Remember this is not preferred in production grade applications to put roles during signup only. Because then anyone can choose any role.
//    private Set<Permission> permissions; // Remember we can skip passing permissions in Signup DTO if we have mapped roles and permissions in user entity.

}
