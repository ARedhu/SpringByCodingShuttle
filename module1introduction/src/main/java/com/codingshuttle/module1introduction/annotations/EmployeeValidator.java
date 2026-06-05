package com.codingshuttle.module1introduction.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeValidator implements ConstraintValidator<EmployeeValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value==null) return false;
        return value.startsWith("EmpName-");
    }
}
