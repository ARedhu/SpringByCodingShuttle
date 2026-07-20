package com.codingShuttle.Ashish.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

// @Component // Remember it is necessary to make it a component so that it's bean can be created by JPA. Else way-2, you can create bean by yourself in config folder.
// Step-3: For createdBy and lastModifiedBy.
// Here, <String> inside of optional and auditor, at both of these places the type of variable should be same. And, it is of the same type as we set the type of createdBy/LastUpdatedBy;
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // When we implement spring security we follow the below 4-steps to get the log-in username.
        // get security context
        // get authentication
        // get principle
        // get username
        return Optional.of("Ashish"); // As of now we are directly setting a username.
    }
}
