package com.codingShuttle.Ashish.SpringSecurity.utils;

import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Permission;
import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codingShuttle.Ashish.SpringSecurity.entities.enums.Permission.*;
import static com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE, POST_UPDATE),
            ADMIN, Set.of(POST_CREATE, POST_UPDATE, POST_DELETE, USER_DELETE, USER_UPDATE, USER_CREATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
