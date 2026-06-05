// This is the blueprint kind of interface that we have to use when we want to define our custom annotation. No need to learn anything from here.
package com.codingshuttle.module1introduction.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeValidator.class)
public @interface EmployeeValidation {

    String message() default "Invalid employee code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
