package com.codingShuttle.Ashish.SpringSecurity.entities;

import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Permission;
import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role;
import com.codingShuttle.Ashish.SpringSecurity.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails { // By implementing UserDetails, we are telling Spring security that This entity represents a user that Spring Security can authenticate and authorize.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // bydefault the enumtype is ordinal which means the roles will be represented by 0, 1, 2.
    private Set<Role> roles;

    // part of way-2:
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Spring security doesn't directly work with our custom: Role.
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) // Remember it is necessary to put "ROLE_" here for roles so that we can use "hasAnyRole" kind of inbuilt functions inside of WebSecurityConfig.
//                .collect(Collectors.toSet());

        //way-2: Till now we have assigned authorities on the basis of role only. But now we can assign authorities on the basis of role as well as permissions/authorities.
//
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(
//                permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))
//        );
//        return authorities;


        // way-3: As we have already done mapping of Permission and Role.
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
